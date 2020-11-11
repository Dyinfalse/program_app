package com.wechat.program.app.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "app_user_role")
public class AppUserRole{

    private Integer userId;

    private Integer roleId;
}
