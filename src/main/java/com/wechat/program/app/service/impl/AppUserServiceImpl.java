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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        appUserMapper.insert(appUser);
        AppUserCombo appUserCombo = new AppUserCombo();
        appUserCombo.setComboId(dto.getComboId());
        appUserCombo.setUserId(appUser.getId());
        appUserCombo.setUsed(true);
        appUserComboService.add(appUserCombo);
        AppUserVo vo = new AppUserVo();
        BeanUtils.copyProperties(appUser, vo);
        vo.setComboId(dto.getComboId());
        SendSmsDto smsDto = new SendSmsDto();
        smsDto.setMobile(dto.getPhone());
        smsService.sendSmsCode(smsDto);
        return vo;
    }

    @Override
    public List<AppUser> searchKey(String keyword) {
        return appUserMapper.selectByKey(keyword);
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
        appUserMapper.updateByPrimaryKeySelective(appUser);
        if (null != dto.getPreComboId() && !dto.getPreComboId().equals(dto.getComboId())) {
            AppUserCombo appUserCombo = new AppUserCombo();
            appUserCombo.setUserId(dto.getId());
            appUserCombo.setComboId(dto.getPreComboId());
            appUserCombo.setUsed(false);
            appUserComboService.updateUsed(appUserCombo);
        }

        return appUser;
    }

    @Override
    public AppUserVo selectByShiroName(String phone) {
        AppUser appUser = appUserMapper.selectByPhone(phone);
        if (Objects.isNull(appUser)) return null;
        List<Integer> roleIds = appUserRoleService.selectRoleIdByUserId(appUser.getId());
        if (CollectionUtils.isEmpty(roleIds)) return null;
        List<AppRole> appRoleList = appRoleService.selectRoles(roleIds);
        if (CollectionUtils.isEmpty(appRoleList)) return null;
        AppUserVo appUserVo = new AppUserVo();
        BeanUtils.copyProperties(appUser, appUserVo);
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
