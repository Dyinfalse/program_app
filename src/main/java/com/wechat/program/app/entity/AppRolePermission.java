package com.wechat.program.app.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "app_role_permission")
public class AppRolePermission{

    private Integer id;

    private Integer roleId;

    private Integer permissionId;

}
