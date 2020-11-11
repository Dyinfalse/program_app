package com.wechat.program.app.entity;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Table(name = "app_role")
@Data
public class AppRole extends BaseEntity {

    private Integer id;

    private String name;

    private String description;

    @Transient
    private List<AppPermission> appPermissionList;
}
