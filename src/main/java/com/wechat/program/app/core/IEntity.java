package com.wechat.program.app.core;

import java.io.Serializable;

public interface IEntity extends Serializable {
    Integer getId();

    void setId(Integer id);
}
