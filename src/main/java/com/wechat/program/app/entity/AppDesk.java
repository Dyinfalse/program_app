package com.wechat.program.app.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "app_desk")
public class AppDesk extends BaseEntity {

    private String name;

    /**
     * '0:未使用，1使用中'
     */
    private Boolean used;
}
