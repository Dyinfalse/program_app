package com.wechat.program.app.service;

import com.wechat.program.app.entity.AppPermission;

import java.util.List;

public interface AppRolePermissionService {

    List<AppPermission> selectAppRolePermissionsByRoleId(Integer roleId);

    List<AppPermission> selectAppPermissions(List<Integer> permissionIds);
}
