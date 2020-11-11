package com.wechat.program.app.service.impl;

import com.wechat.program.app.entity.AppCombo;
import com.wechat.program.app.exception.ProgramException;
import com.wechat.program.app.mapper.AppComboMapper;
import com.wechat.program.app.request.AppComboDTO;
import com.wechat.program.app.service.AppComboService;
import com.wechat.program.app.service.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AppComboServiceImpl extends BaseService<AppCombo> implements AppComboService {

    private AppComboMapper appComboMapper;

    @Override
    public AppCombo add(AppComboDTO dto) {
        AppCombo entity = appComboMapper.selectByName(dto.getName());
        if (Objects.nonNull(entity)) throw new ProgramException("套餐名称重复， 请修改！");
        AppCombo appCombo = new AppCombo();
        BeanUtils.copyProperties(dto, appCombo);
        insert(appCombo);
        return appCombo;
    }

    @Autowired
    public void setAppComboMapper(AppComboMapper appComboMapper) {
        this.appComboMapper = appComboMapper;
    }
}
