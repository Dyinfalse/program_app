package com.wechat.program.app.service;

import com.wechat.program.app.entity.AppUserCombo;

import java.util.List;

public interface AppUserComboService {

    AppUserCombo add(AppUserCombo appUserCombo);

    void updateUsed(AppUserCombo appUserCombo);

    List<AppUserCombo> selectByUserId(Integer userId);

    Integer selectComboIdByUserId(Integer userId);
}
