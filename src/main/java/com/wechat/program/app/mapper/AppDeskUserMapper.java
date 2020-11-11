package com.wechat.program.app.mapper;

import com.wechat.program.app.core.IBaseMapper;
import com.wechat.program.app.entity.AppDeskUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDeskUserMapper extends IBaseMapper<AppDeskUser> {

    AppDeskUser selectAppDeskUserByDeskIdAndNotFinished(@Param("deskId") Integer deskId, @Param("status") Integer status);

    Integer selectCountStatusByFinish(@Param("userId") Integer userId);
}
