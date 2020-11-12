package com.wechat.program.app.service.impl;

import com.wechat.program.app.entity.AppDesk;
import com.wechat.program.app.entity.AppDeskUser;
import com.wechat.program.app.entity.AppUser;
import com.wechat.program.app.exception.ProgramException;
import com.wechat.program.app.mapper.AppDeskUserMapper;
import com.wechat.program.app.request.AppDeskUserDTO;
import com.wechat.program.app.request.AppDeskUserStatusDTO;
import com.wechat.program.app.request.SendSmsDto;
import com.wechat.program.app.service.*;
import com.wechat.program.app.utils.DateUtil;
import com.wechat.program.app.vo.AppDeskVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AppDeskUserServiceImpl extends BaseService<AppDeskUser> implements AppDeskUserService {

    private AppDeskService appDeskService;

    private AppDeskUserMapper appDeskUserMapper;

    private AppUserService appUserService;

    private SmsService smsService;

    @Override
    public AppDeskUser add(AppDeskUserDTO dto) {
        AppDeskUser appDeskUser = new AppDeskUser();
        BeanUtils.copyProperties(dto, appDeskUser);
        appDeskUser.setStatus(1);
        appDeskUser.setRecordTime(new Date());
        insert(appDeskUser);
        appDeskService.updateUsed(appDeskUser.getDeskId(), true);
        return appDeskUser;
    }

    @Override
    public void updateStatus(AppDeskUserStatusDTO dto) {
        AppDeskUser appDeskUser = appDeskUserMapper.selectByPrimaryKey(dto.getId());
        if (Objects.isNull(appDeskUser)) throw new ProgramException("无此桌子信息！");
        if (dto.getStatus() == 0) {
            int consumptionTime = DateUtil.getMin(appDeskUser.getRecordTime(), new Date());
            appDeskUser.setConsumptionTime(consumptionTime + appDeskUser.getConsumptionTime());
        } else if (dto.getStatus() == 1) {
            appDeskUser.setRecordTime(new Date());
        }
        appDeskUser.setStatus(dto.getStatus());
        appDeskUserMapper.updateByPrimaryKeySelective(appDeskUser);
        // 如果结束的话，查找本桌子id相关的单子是否还有非结束的，如果有，则桌子为使用状态，否则为闲置状态
        if (dto.getStatus() == 2) {
            int consumptionTime = DateUtil.getMin(appDeskUser.getRecordTime(), new Date());
            appDeskUser.setConsumptionTime(consumptionTime + appDeskUser.getConsumptionTime());
            appDeskUser.setStatus(2);
            appDeskUserMapper.updateByPrimaryKeySelective(appDeskUser);
            int status = appDeskUserMapper.selectCountStatusByFinish(appDeskUser.getUserId());
            if (status == 0) appDeskService.updateUsed(appDeskUser.getDeskId(), false);
            AppUser appUser = appUserService.selectByKey(appDeskUser.getUserId());
            Integer duration = appUserService.getComboOfDuration(appDeskUser.getUserId());
            duration += appUser.getPresentTime();
            appUser.setTotalTime(appUser.getTotalTime()-appDeskUser.getConsumptionTime());
            appUserService.update(appUser);
            SendSmsDto sendSmsDto = new SendSmsDto(appUser.getPhone(), 2);
            ArrayList<String> params = sendSmsDto.getParams();
            params.add(appUser.getName());
            params.add(String.valueOf(consumptionTime));
//            params.add(String.valueOf(duration-appDeskUser.getConsumptionTime()));
            params.add(String.valueOf(appUser.getTotalTime()));
            sendSmsDto.setParams(params);
            smsService.sendSmsCode(sendSmsDto);
        }
    }

    @Override
    public List<AppDeskVo> selectDeskList() {
        List<AppDesk> appDesks = appDeskService.selectAll();
        List<AppDeskVo> voList = new ArrayList<>();
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
//                    vo.setUserInfo((appUser.getMember() ? "会员_" : "非会员_") + appUser.getName() + "_" + appUser.getPhone());
                    vo.setUserInfo(appUser.getName());
                    vo.setRecordTime(appDeskUser.getRecordTime());
                    // 套餐时长
//                    Integer duration = appUserService.getComboOfDuration(appDeskUser.getUserId());
//                    duration += appUser.getPresentTime();
                    Integer totalTime = appUser.getTotalTime();
                    Integer duration = totalTime + appUser.getPresentTime();
                    if (appDeskUser.getStatus() == 1) {
                        // 消费时长
                        int consumptionTime = DateUtil.getMin(appDeskUser.getRecordTime(), new Date());
                        vo.setConsumptionTime(consumptionTime + appDeskUser.getConsumptionTime());
                        // 剩余时长
                        vo.setRemainingTime(duration - consumptionTime);
                    } else {
                        Integer consumptionTime = appDeskUser.getConsumptionTime();
                        vo.setConsumptionTime(consumptionTime);
                        vo.setRemainingTime(duration - consumptionTime);
                    }
                }
            }
            voList.add(vo);
        }
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
}
