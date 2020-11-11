package com.wechat.program.app.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRoleMapper {

    List<Integer> selectRoleIdByUserId(@Param("userId") Integer userId);
}
