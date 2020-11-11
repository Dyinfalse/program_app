package com.wechat.program.app.request;

import lombok.Data;

import java.util.Date;

@Data
public class AppUserDTO {

    private Integer id;

    private String name;

    private String phone;

    private Boolean member = false;

    private String password;

    private Integer type;

    private Integer comboId;

    private Integer preComboId;

    private Integer sex;

    private Integer currentIntegral;

    private Integer coupon;

    private Date validityVolume;
}
