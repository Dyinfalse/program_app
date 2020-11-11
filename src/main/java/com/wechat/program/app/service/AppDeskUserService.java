package com.wechat.program.app.service;

import com.wechat.program.app.core.IService;
import com.wechat.program.app.entity.AppDeskUser;
import com.wechat.program.app.request.AppDeskUserDTO;
import com.wechat.program.app.request.AppDeskUserStatusDTO;
import com.wechat.program.app.vo.AppDeskVo;

import java.util.List;

public interface AppDeskUserService extends IService<AppDeskUser> {

    AppDeskUser add(AppDeskUserDTO dto);

    void updateStatus(AppDeskUserStatusDTO dto);

    List<AppDeskVo> selectDeskList();
}
