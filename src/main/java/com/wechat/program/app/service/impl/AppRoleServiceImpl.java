package com.wechat.program.app.service.impl;

import com.wechat.program.app.entity.AppPermission;
import com.wechat.program.app.entity.AppRole;
import com.wechat.program.app.entity.AppRolePermission;
import com.wechat.program.app.mapper.AppRoleMapper;
import com.wechat.program.app.service.AppPermissionService;
import com.wechat.program.app.service.AppRolePermissionService;
import com.wechat.program.app.service.AppRoleService;
import com.wechat.program.app.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class AppRoleServiceImpl extends BaseService<AppRole> implements AppRoleService {

    private AppRoleMapper appRoleMapper;

    private AppRolePermissionService appRolePermissionService;

    @Override
    public List<AppRole> selectRoles(List<Integer> roleIds) {
       List<AppRole> appRoleList =  appRoleMapper.selectRoles(roleIds);
       if (CollectionUtils.isEmpty(appRoleList))return Collections.emptyList();
       for (AppRole appRole : appRoleList) {
           List<AppPermission> permissionList = appRolePermissionService.selectAppRolePermissionsByRoleId(appRole.getId());
           appRole.setAppPermissionList(permissionList);
       }
        return appRoleList;
    }

    @Autowired
    public void setAppRolePermissionService(AppRolePermissionService appRolePermissionService) {
        this.appRolePermissionService = appRolePermissionService;
    }

    @Autowired
    public void setAppRoleMapper(AppRoleMapper appRoleMapper) {
        this.appRoleMapper = appRoleMapper;
    }
}
