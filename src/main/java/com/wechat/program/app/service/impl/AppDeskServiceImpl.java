package com.wechat.program.app.service.impl;

import com.wechat.program.app.entity.AppDesk;
import com.wechat.program.app.mapper.AppDeskMapper;
import com.wechat.program.app.service.AppDeskService;
import com.wechat.program.app.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppDeskServiceImpl extends BaseService<AppDesk> implements AppDeskService {

    private AppDeskMapper appDeskMapper;

    @Override
    public void updateUsed(Integer id, Boolean used) {
        AppDesk appDesk = new AppDesk();
        appDesk.setUsed(used);
        appDesk.setId(id);
        appDeskMapper.updateByPrimaryKeySelective(appDesk);
    }


    @Autowired
    public void setAppDeskMapper(AppDeskMapper appDeskMapper) {
        this.appDeskMapper = appDeskMapper;
    }
}
