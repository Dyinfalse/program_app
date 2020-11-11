package com.wechat.program.app.entity;


import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "app_user")
public class AppUser extends BaseEntity {

    private String name;

    private String phone;

    private Boolean member;

    private String password;

    /**
     * 0:客户，1：员工，2：管理员'
     */
    private Integer type;

    private Integer sex;

    private Integer currentIntegral;

    private Integer coupon;

    private Date validityVolume;

}
