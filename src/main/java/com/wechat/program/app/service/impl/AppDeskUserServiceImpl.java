package com.wechat.program.app.service.impl;

import com.wechat.program.app.entity.AppDesk;
import com.wechat.program.app.entity.AppDeskUser;
import com.wechat.program.app.entity.AppUser;
import com.wechat.program.app.entity.BaseEntity;
import com.wechat.program.app.exception.ProgramException;
import com.wechat.program.app.mapper.AppDeskUserMapper;
import com.wechat.program.app.request.AppDeskUserDTO;
import com.wechat.program.app.request.AppDeskUserStatusDTO;
import com.wechat.program.app.request.SendSmsDto;
import com.wechat.program.app.service.*;
import com.wechat.program.app.utils.DateUtil;
import com.wechat.program.app.vo.AppDeskVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppDeskUserServiceImpl extends BaseService<AppDeskUser> implements AppDeskUserService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AppDeskService appDeskService;

    private AppDeskUserMapper appDeskUserMapper;

    private AppUserService appUserService;

    private SmsService smsService;

    private AppUserComboService appUserComboService;

    @Override
    public AppDeskUser add(AppDeskUserDTO dto) {
        AppDesk appDesk = appDeskService.selectByKey(dto.getDeskId());
        if (Objects.isNull(appDesk) || appDesk.getUsed()) throw new ProgramException("此桌正在使用中！");
        AppDeskUser appDeskUser = new AppDeskUser();
        BeanUtils.copyProperties(dto, appDeskUser);
        appDeskUser.setStatus(1);
        appDeskUser.setRecordTime(new Date());
        insert(appDeskUser);
        appDeskService.updateUsed(appDeskUser.getDeskId(), true);
        return appDeskUser;
    }

    @Override
    public synchronized Map updateStatus(AppDeskUserStatusDTO dto) {
        AppDeskUser appDeskUser = appDeskUserMapper.selectByPrimaryKey(dto.getId());
        if (Objects.isNull(appDeskUser)) throw new ProgramException("无此桌子信息！");
        logger.info("updateStatus appDeskUser原信息: {}",   appDeskUser);
        logger.info("updateStatus 修改状态: {}", dto.getStatus());
        if (appDeskUser.getStatus().equals(dto.getStatus())) {
            return null;
        }
        if (dto.getStatus() == 0) {
            if(appDeskUser.getStatus() == 2) {
                throw new ProgramException("此桌订单已经结束！请刷新");
            }
            int pauseNum = appDeskUser.getPauseNum();
            int consumptionTime = DateUtil.getMin(appDeskUser.getRecordTime(), new Date());
            if (pauseNum >= 1) {
                appDeskUser.setConsumptionTime(consumptionTime + appDeskUser.getConsumptionTime());
            } else {
                appDeskUser.setConsumptionTime(consumptionTime);
            }
            appDeskUser.setPauseTime(new Date());
            logger.info("updateStatus 修改状态: {}, 计算之后的消费时长 {}", dto.getStatus(), appDeskUser.getConsumptionTime());
            appDeskUser.setPauseNum(pauseNum+1);
        } else if (dto.getStatus() == 1) {
            appDeskUser.setRecordTime(new Date());
            if (appDeskUser.getStatus() == 0 && appDeskUser.getPauseNum() > 0) {
                Integer pauseTime = DateUtil.getSeconds(appDeskUser.getPauseTime(), new Date());
                logger.info("updateStatus  {} 号桌子, 第{}次暂停结束，本次暂停: {} 秒", appDeskUser.getDeskId(), appDeskUser.getPauseNum(), pauseTime);
                Integer pauseTotalTime = pauseTime + appDeskUser.getPauseTotalTime();
                appDeskUser.setPauseTotalTime(pauseTotalTime);
            }
        }
        // 如果结束的话，查找本桌子id相关的单子是否还有非结束的，如果有，则桌子为使用状态，否则为闲置状态
        if (dto.getStatus() == 2) {
            if (appDeskUser.getStatus() != 0) {
                int consumptionTime = DateUtil.getMin(appDeskUser.getRecordTime(), new Date());
                appDeskUser.setConsumptionTime(consumptionTime + appDeskUser.getConsumptionTime());
            }
            appDeskUser.setStatus(2);
            appDeskUserMapper.updateByPrimaryKeySelective(appDeskUser);
            logger.info("updateStatus 停止后appDeskUser详情: {}", appDeskUser);
            int status = appDeskUserMapper.selectCountStatusByFinish(appDeskUser.getUserId());
            if (status == 0) appDeskService.updateUsed(appDeskUser.getDeskId(), false);
            AppUser appUser = appUserService.selectByKey(appDeskUser.getUserId());
//            Integer duration = appUserService.getComboOfDuration(appDeskUser.getUserId());
//            duration += appUser.getPresentTime();
            Integer duration = appUser.getTotalTime();
            appUser.setTotalTime(appUser.getTotalTime() - appDeskUser.getConsumptionTime());
            appUserService.updateByPrimaryKeyOverSelective(appUser);
            SendSmsDto sendSmsDto = new SendSmsDto(appUser.getPhone(), 2);
            ArrayList<String> params = sendSmsDto.getParams();
            params.add(appUser.getName());
            params.add(String.valueOf(appDeskUser.getConsumptionTime()));
            params.add(String.valueOf(duration-appDeskUser.getConsumptionTime()));
//            params.add(String.valueOf(appUser.getTotalTime()));
            sendSmsDto.setParams(params);
            smsService.sendSmsCode(sendSmsDto);
        } else {
            appDeskUser.setStatus(dto.getStatus());
            appDeskUserMapper.updateByPrimaryKeySelective(appDeskUser);
            logger.info("updateStatus 非停止appDeskUser详情: {}", appDeskUser);
        }

        AppUser appUser = appUserService.selectByKey(appDeskUser.getUserId());
        Map vo = new HashMap<String, Integer>();
        if(!Objects.isNull(appUser)){
            vo.put("totalTime", appUser.getTotalTime());
            vo.put("consumptionTime", appDeskUser.getConsumptionTime());
        }
        return vo;
    }


    @Override
    public List<AppDeskUser> consumptionStatisticsByUserId (Integer userId) {
        if (Objects.isNull(userId)) throw new ProgramException("userId为空！");
        return appDeskUserMapper.selectAppDeskUserStatisticsByUserId(userId);
    }

    @Override
    public List<AppDeskVo> selectDeskList() {
        List<AppDesk> appDesks = appDeskService.selectAll();
        List<AppDeskVo> voList = new ArrayList<>();
        Map<Integer, Integer> map =new HashMap<>();
        for (AppDesk appDesk : appDesks) {
            AppDeskVo vo = new AppDeskVo();
            vo.setDeskId(appDesk.getId());
            vo.setUsed(appDesk.getUsed());
            vo.setName(appDesk.getName());
            if (appDesk.getUsed()) {
                AppDeskUser appDeskUser = appDeskUserMapper.selectAppDeskUserByDeskIdAndNotFinished(appDesk.getId(), 2);
                if (Objects.nonNull(appDeskUser) && null != appDeskUser.getUserId()) {
                    BeanUtils.copyProperties(appDeskUser, vo);
                    vo.setId(appDeskUser.getId());
                    AppUser appUser = appUserService.selectByKey(appDeskUser.getUserId());
                    if (Objects.isNull(appUser)) continue;
                    vo.setUserId(appDeskUser.getUserId());
                    vo.setUserInfo(appUser.getName());
                    vo.setRecordTime(appDeskUser.getRecordTime());
                    vo.setPauseTime(appDeskUser.getPauseTime());
                    vo.setPauseTotalTime(appDeskUser.getPauseTotalTime());
                    // 套餐时长
//                    Integer duration = appUserService.getComboOfDuration(appDeskUser.getUserId());
//                    duration += appUser.getPresentTime();
                    Integer duration = appUser.getTotalTime();
                    if (appDeskUser.getStatus() == 1) {
                        // 消费时长
                        Date nowDate = new Date();
                        int consumptionTime = DateUtil.getMin(appDeskUser.getRecordTime(), nowDate);
                        int pauseNum = appDeskUser.getPauseNum();
                        if (pauseNum >= 1) {
                            vo.setConsumptionTime(consumptionTime + appDeskUser.getConsumptionTime());
//                            vo.setRecordTime(nowDate);
//                            appDeskUser.setRecordTime(nowDate);
//                            appDeskUser.setConsumptionTime(vo.getConsumptionTime());
                            vo.setRecordTime(appDeskUser.getCreateTime());
                        } else {
                            vo.setConsumptionTime(consumptionTime);
                            appDeskUser.setConsumptionTime(consumptionTime);
                        }
                        // 剩余时长
                        vo.setRemainingTime(duration - vo.getConsumptionTime());

                        map.put(vo.getUserId(), map.getOrDefault(vo.getUserId(), duration) - vo.getConsumptionTime());
                    } else if (appDeskUser.getStatus() == 0) {
                        Integer consumptionTime = appDeskUser.getConsumptionTime();
                        vo.setConsumptionTime(consumptionTime);
                        vo.setRemainingTime(duration - vo.getConsumptionTime());
                        vo.setRecordTime(appDeskUser.getCreateTime());
                        map.put(vo.getUserId(), map.getOrDefault(vo.getUserId(), duration) - vo.getConsumptionTime());
                        appDeskUserMapper.updateByPrimaryKeySelective(appDeskUser);
                    }
//                    appUserService.update(appUser);
                    appUser.setComboId(appUserComboService.selectComboIdByUserId(appUser.getId()));
                    vo.setAppUser(appUser);
                }
            }

            voList.add(vo);
        }
        voList.forEach(t->{
            if (t.getId()!= null) {
                Integer remainingTime = map.get(t.getUserId());
                if (remainingTime != null) {
                    t.setRemainingTime(remainingTime);
                }
            }
        });
        return voList;
    }

    @Autowired
    public void setAppDeskService(AppDeskService appDeskService) {
        this.appDeskService = appDeskService;
    }

    @Autowired
    public void setAppDeskUserMapper(AppDeskUserMapper appDeskUserMapper) {
        this.appDeskUserMapper = appDeskUserMapper;
    }

    @Autowired
    public void setAppUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Autowired
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }

    @Autowired
    public void setAppUserComboService(AppUserComboService appUserComboService) {
        this.appUserComboService = appUserComboService;
    }
}
