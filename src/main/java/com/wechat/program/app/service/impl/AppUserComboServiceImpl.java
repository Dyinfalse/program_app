package com.wechat.program.app.service.impl;

import com.wechat.program.app.entity.AppUserCombo;
import com.wechat.program.app.mapper.AppUserComboMapper;
import com.wechat.program.app.service.AppUserComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserComboServiceImpl implements AppUserComboService {

    private AppUserComboMapper appUserComboMapper;

    @Override
    public AppUserCombo add(AppUserCombo appUserCombo) {
        appUserComboMapper.insertAppUserCombo(appUserCombo);
        return appUserCombo;
    }

    @Override
    public void updateUsed(AppUserCombo appUserCombo) {
        appUserComboMapper.updateUsed(appUserCombo);
    }

    @Override
    public List<AppUserCombo> selectByUserId(Integer usedId) {
        return appUserComboMapper.selectByUserId(usedId);
    }

    @Override
    public Integer selectComboIdByUserId(Integer userId) {
        return appUserComboMapper.selectComboIdByUserId(userId);
    }

    @Autowired
    public void setAppUserComboMapper(AppUserComboMapper appUserComboMapper) {
        this.appUserComboMapper = appUserComboMapper;
    }
}
