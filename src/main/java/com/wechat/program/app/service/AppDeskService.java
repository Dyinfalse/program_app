package com.wechat.program.app.service;

import com.wechat.program.app.core.IService;
import com.wechat.program.app.entity.AppCombo;
import com.wechat.program.app.entity.AppDesk;
import com.wechat.program.app.request.AppComboDTO;

import java.util.List;

public interface AppDeskService extends IService<AppDesk> {


    void updateUsed(Integer id, Boolean used);
}
