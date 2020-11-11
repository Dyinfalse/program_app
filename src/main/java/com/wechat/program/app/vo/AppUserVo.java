package com.wechat.program.app.vo;

import com.wechat.program.app.entity.AppRole;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AppUserVo implements Serializable {

    private String name;

    private String phone;

    private Boolean member;

    private String password;

    private Integer comboId;

    private Integer sex;

    private Integer currentIntegral;

    private Integer coupon;

    private Integer type;

    private Date validityVolume;

    private List<AppRole> appRoleList;
}
