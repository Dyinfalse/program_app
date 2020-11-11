package com.wechat.program.app.mapper;

import com.wechat.program.app.core.IBaseMapper;
import com.wechat.program.app.entity.AppRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRoleMapper extends IBaseMapper<AppRole> {

    List<AppRole> selectRoles(@Param("ids") List<Integer> roleIds);

}
