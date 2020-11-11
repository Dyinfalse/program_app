package com.wechat.program.app.service.impl;

import com.wechat.program.app.entity.AppPermission;
import com.wechat.program.app.entity.AppRolePermission;
import com.wechat.program.app.mapper.AppRolePermissionMapper;
import com.wechat.program.app.service.AppPermissionService;
import com.wechat.program.app.service.AppRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppRolePermissionServiceImpl  implements AppRolePermissionService {

    private AppRolePermissionMapper appRolePermissionMapper;

    private AppPermissionService appPermissionService;

    @Override
    public List<AppPermission> selectAppRolePermissionsByRoleId(Integer roleId) {
        List<AppRolePermission> appRolePermissionList = appRolePermissionMapper.selectAppRolePermissionsByRoleId(roleId);
        if(CollectionUtils.isEmpty(appRolePermissionList)) return Collections.emptyList();
        List<Integer> permissionIds = appRolePermissionList.stream().map(AppRolePermission::getPermissionId).collect(Collectors.toList());
        return selectAppPermissions(permissionIds);
    }

    @Override
    public List<AppPermission> selectAppPermissions(List<Integer> permissionIds) {
        return appPermissionService.selectAppPermissions(permissionIds);
    }

    @Autowired
    public void setAppRolePermissionMapper(AppRolePermissionMapper appRolePermissionMapper) {
        this.appRolePermissionMapper = appRolePermissionMapper;
    }

    @Autowired
    public void setAppPermissionService(AppPermissionService appPermissionService) {
        this.appPermissionService = appPermissionService;
    }
}
