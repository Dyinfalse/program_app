package com.wechat.program.app.service;

import com.wechat.program.app.core.IService;
import com.wechat.program.app.entity.AppPermission;

import java.util.List;

public interface AppPermissionService extends IService<AppPermission> {

    List<AppPermission> selectAppPermissions(List<Integer> permissionIds);
}
