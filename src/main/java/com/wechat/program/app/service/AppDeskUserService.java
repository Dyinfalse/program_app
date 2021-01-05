package com.wechat.program.app.service;

import com.wechat.program.app.core.IService;
import com.wechat.program.app.entity.AppDesk;
import com.wechat.program.app.entity.AppDeskUser;
import com.wechat.program.app.request.AppDeskUserDTO;
import com.wechat.program.app.request.AppDeskUserStatusDTO;
import com.wechat.program.app.vo.AppDeskVo;

import java.util.List;
import java.util.Map;

public interface AppDeskUserService extends IService<AppDeskUser> {

    AppDeskUser add(AppDeskUserDTO dto);

    Map updateStatus(AppDeskUserStatusDTO dto);

    List<AppDeskVo> selectDeskList();

    List<AppDeskUser> consumptionStatisticsByUserId(Integer userId);
}
