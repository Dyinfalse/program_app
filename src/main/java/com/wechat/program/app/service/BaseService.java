package com.wechat.program.app.service;

import com.wechat.program.app.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BaseService<T extends BaseEntity> extends ABaseService<T> {


    @Autowired
    protected ApplicationContext applicationContext;
}
