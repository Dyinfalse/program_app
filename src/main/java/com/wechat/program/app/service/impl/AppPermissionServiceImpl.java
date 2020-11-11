package com.wechat.program.app.service.impl;

import com.wechat.program.app.entity.AppPermission;
import com.wechat.program.app.mapper.AppPermissionMapper;
import com.wechat.program.app.service.AppPermissionService;
import com.wechat.program.app.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppPermissionServiceImpl extends BaseService<AppPermission> implements AppPermissionService {

    private AppPermissionMapper appPermissionMapper;

    @Override
    public List<AppPermission> selectAppPermissions(List<Integer> permissionIds) {
     return appPermissionMapper.selectAppPermissions(permissionIds);
    }

    @Autowired
    public void setAppPermissionMapper(AppPermissionMapper appPermissionMapper) {
        this.appPermissionMapper = appPermissionMapper;
    }
}
