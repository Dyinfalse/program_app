package com.wechat.program.app.mapper;

import com.wechat.program.app.entity.AppRolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRolePermissionMapper {

    List<AppRolePermission> selectAppRolePermissionsByRoleId(@Param("roleId") Integer roleId);
}
