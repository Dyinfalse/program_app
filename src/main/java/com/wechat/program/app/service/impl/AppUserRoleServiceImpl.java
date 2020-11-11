package com.wechat.program.app.service.impl;

import com.wechat.program.app.mapper.AppUserRoleMapper;
import com.wechat.program.app.service.AppUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserRoleServiceImpl  implements AppUserRoleService {

    private AppUserRoleMapper appUserRoleMapper;

    @Override
    public List<Integer> selectRoleIdByUserId(Integer userId) {
        return appUserRoleMapper.selectRoleIdByUserId(userId);
    }

    @Autowired
    public void setAppUserRoleMapper(AppUserRoleMapper appUserRoleMapper) {
        this.appUserRoleMapper = appUserRoleMapper;
    }
}
