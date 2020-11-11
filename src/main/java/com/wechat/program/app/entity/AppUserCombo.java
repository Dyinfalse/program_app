package com.wechat.program.app.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "app_user_combo")
public class AppUserCombo {

    private Integer userId;

    private Integer comboId;

    private Boolean used = false;
}
