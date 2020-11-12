package com.wechat.program.app.service.impl;

import com.wechat.program.app.entity.AppCombo;
import com.wechat.program.app.entity.AppRole;
import com.wechat.program.app.entity.AppUser;
import com.wechat.program.app.entity.AppUserCombo;
import com.wechat.program.app.exception.ProgramException;
import com.wechat.program.app.mapper.AppUserMapper;
import com.wechat.program.app.request.AppUserDTO;
import com.wechat.program.app.request.SendSmsDto;
import com.wechat.program.app.service.*;
import com.wechat.program.app.utils.SHAUtil;
import com.wechat.program.app.vo.AppUserVo;
import com.wechat.program.app.vo.UserConsumptionStatisticsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class AppUserServiceImpl extends BaseService<AppUser> implements AppUserService {

    private AppUserMapper appUserMapper;

    private AppRoleService appRoleService;

    private AppUserRoleService appUserRoleService;

    private AppUserComboService appUserComboService;

    private AppComboService appComboService;

    private SmsService smsService;

    @Transactional
    @Override
    public AppUserVo add(AppUserDTO dto) {
        AppUser appUser = new AppUser();
        if (Objects.isNull(appComboService.selectByKey(dto.getComboId()))) throw new ProgramException("套餐不存在！");
        BeanUtils.copyProperties(dto, appUser);
        String password = SHAUtil.SHA256(appUser.getPassword());
        appUser.setPassword(password);
        appUser.setToken(SHAUtil.SHA256(appUser.getPhone()+appUser.getPassword()));
        AppUserCombo appUserCombo = new AppUserCombo();
        appUserCombo.setComboId(dto.getComboId());
        appUserCombo.setUsed(true);
        AppCombo appCombo = appComboService.selectByKey(dto.getComboId());
        if (Objects.isNull(appCombo)) throw new ProgramException("套餐不存在！");
        appUser.setTotalTime(appCombo.getDuration());
        appUserMapper.insert(appUser);
        appUserCombo.setUserId(appUser.getId());
        appUserComboService.add(appUserCombo);

        AppUserVo vo = new AppUserVo();
        BeanUtils.copyProperties(appUser, vo);
        vo.setComboId(dto.getComboId());
        SendSmsDto smsDto = new SendSmsDto();
        smsDto.setMobile(dto.getPhone());
        smsDto.setType(1);
        int consumption = dto.getPresentTime() + appCombo.getDuration();
        ArrayList<String> params = smsDto.getParams();
        params.add(appUser.getName());
        params.add(appCombo.getName().replace("会员", ""));
        params.add(String.valueOf(consumption));
        smsDto.setParams(params);
        smsService.sendSmsCode(smsDto);
        return vo;
    }

    @Override
    public List<AppUser> searchKey(String keyword) {
        List<AppUser> appUsers = appUserMapper.selectByKey(keyword);
        if (StringUtils.isEmpty(appUsers)) return appUsers;
        appUsers.forEach(t-> {
            Integer comboId = appUserComboService.selectComboIdByUserId(t.getId());
            if (comboId != null) t.setComboId(comboId);
        });
        return appUsers;
    }

    @Override
    public List<AppUser> members(Integer type) {
        return appUserMapper.selectMembers(type);
    }

    @Override
    public AppUser selectNameAndPassword(String name, String password) {
        String passWord = SHAUtil.SHA256(password);
        return appUserMapper.selectNameAndPassword(name, passWord);
    }

    @Override
    public AppUser selectByPhone(String phone) {
        return appUserMapper.selectByPhone(phone);
    }

    @Transactional
    @Override
    public AppUser updateAppUser(AppUserDTO dto) {
        if (null == dto.getId()) throw new ProgramException("修改用户信息缺失！");
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(dto, appUser);
        if (null != dto.getPreComboId() && dto.getPreComboId() > 0 &&  !dto.getPreComboId().equals(dto.getComboId())) {
            AppCombo appCombo = appComboService.selectByKey(dto.getPreComboId());
            if (Objects.isNull(appCombo)) throw new ProgramException("套餐不存在！");
            AppUserCombo appUserCombo = new AppUserCombo();
            appUserCombo.setUserId(dto.getId());
            appUserCombo.setComboId(dto.getPreComboId());
            appUserCombo.setUsed(true);
            appUserComboService.updateUsed(appUserCombo);
            AppUserCombo combo = new AppUserCombo();
            combo.setUserId(dto.getId());
            combo.setComboId(dto.getComboId());
            combo.setUsed(false);
            appUserComboService.updateUsed(combo);
            appUser.setTotalTime(appUser.getTotalTime()+appCombo.getDuration());
        }
        appUserMapper.updateByPrimaryKeySelective(appUser);
        return appUser;
    }

    @Override
    public AppUserVo selectByShiroName(String phone) {
        AppUser appUser = appUserMapper.selectNoMembersByPhone(phone);
        if (Objects.isNull(appUser)) return null;
        AppUserVo appUserVo = new AppUserVo();
        BeanUtils.copyProperties(appUser, appUserVo);
        List<Integer> roleIds = appUserRoleService.selectRoleIdByUserId(appUser.getId());
        if (CollectionUtils.isEmpty(roleIds)) return appUserVo;
        List<AppRole> appRoleList = appRoleService.selectRoles(roleIds);
        if (CollectionUtils.isEmpty(appRoleList)) return null;
        appUserVo.setAppRoleList(appRoleList);
        return appUserVo;
    }

    @Override
    public Integer getComboOfDuration(Integer userId) {
        Integer duration = appUserMapper.getComboOfDuration(userId);
        return Objects.isNull(duration) ? 0 : duration;
    }

    @Override
    public List<AppUser> selectListByType(Integer type) {
        return appUserMapper.selectListByType(type);
    }

    @Override
    public List<UserConsumptionStatisticsVO> consumptionStatistics() {
        List<AppUser> members = members(1);
        if (CollectionUtils.isEmpty(members)) return Collections.emptyList();
        List<UserConsumptionStatisticsVO> consumptionStatisticsVOList = new ArrayList<>();
        for (AppUser appUser : members) {
            UserConsumptionStatisticsVO vo = new UserConsumptionStatisticsVO();
            vo.setId(appUser.getId());
            vo.setName(appUser.getName());
            vo.setPhone(appUser.getPhone());
            vo.setPresentTime(appUser.getPresentTime());
            vo.setTotalTime(appUser.getTotalTime());
            List<AppUserCombo> appUserCombos = appUserComboService.selectByUserId(appUser.getId());
            if (!CollectionUtils.isEmpty(appUserCombos)) {
                List<AppCombo> appCombos = new ArrayList<>();
                for (AppUserCombo appUserCombo : appUserCombos) {
                    AppCombo appCombo = appComboService.selectByKey(appUserCombo.getComboId());
                    if (Objects.nonNull(appCombo)) appCombos.add(appCombo);
                }
                vo.setComboList(appCombos);
            }
            consumptionStatisticsVOList.add(vo);
        }
        return consumptionStatisticsVOList;
    }

    @Override
    public Boolean selectByToken(String token) {
        return appUserMapper.selectByToken(token) > 0;
    }

    @Override
    public void updateToken(String token, Integer id) {
        AppUser appUser = new AppUser();
        appUser.setToken(token);
        appUser.setId(id);
        appUserMapper.updateToken(appUser);
    }

    @Override
    public Integer selectByTokenAndPermissionCode(String token, String code) {
        return appUserMapper.selectByTokenAndPermissionCode(token, code);
    }

    @Override
    public AppUser selectByPhoneAndPassword(String phone, String password) {
        password = SHAUtil.SHA256(password);
        return appUserMapper.selectByPhoneAndPassword(phone, password);
    }

    @Autowired
    public void setAppUserMapper(AppUserMapper appUserMapper) {
        this.appUserMapper = appUserMapper;
    }

    @Autowired
    public void setAppRoleService(AppRoleService appRoleService) {
        this.appRoleService = appRoleService;
    }

    @Autowired
    public void setAppUserRoleService(AppUserRoleService appUserRoleService) {
        this.appUserRoleService = appUserRoleService;
    }

    @Autowired
    public void setAppUserComboService(AppUserComboService appUserComboService) {
        this.appUserComboService = appUserComboService;
    }

    @Autowired
    public void setAppComboService(AppComboService appComboService) {
        this.appComboService = appComboService;
    }

    @Autowired
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }
}
