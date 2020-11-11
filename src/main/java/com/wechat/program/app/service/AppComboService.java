package com.wechat.program.app.service;

import com.wechat.program.app.core.IService;
import com.wechat.program.app.entity.AppCombo;
import com.wechat.program.app.request.AppComboDTO;

public interface AppComboService extends IService<AppCombo> {

    AppCombo add(AppComboDTO dto);
}
