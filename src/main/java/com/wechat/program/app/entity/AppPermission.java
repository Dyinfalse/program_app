package com.wechat.program.app.entity;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "app_permission")
@Data
public class AppPermission extends BaseEntity {

    private String code;

    private String name;
}
