package com.wechat.program.app.mapper;

import com.wechat.program.app.core.IBaseMapper;
import com.wechat.program.app.entity.AppPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppPermissionMapper extends IBaseMapper<AppPermission> {

    List<AppPermission> selectAppPermissions(@Param("ids") List<Integer> permissionIds);
}
