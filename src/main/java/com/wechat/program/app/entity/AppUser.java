package com.wechat.program.app.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Table(name = "app_user")
public class AppUser extends BaseEntity {

    private String name;

    private String phone;

    private Boolean member;

    private String password;

    private String token;

    /**
     * 0:客户，1：员工，2：管理员'
     */
    private Integer type;

    private Integer sex;

    /**
     * 生日
     */

    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date birthday;

    /**
     * 赠送时长
     */
    private Integer presentTime = 0;

    /**
     * 总时长
     */
    private Integer totalTime;

    private Integer currentIntegral;

    private Integer coupon;

    private Date validityVolume;

    @Transient
    private Integer comboId;

//    public Integer getTotalTime() {
//        return totalTime == null ? 0 : totalTime;
//    }

    public Integer getPresentTime() {
        return presentTime == null ? 0 : presentTime;
    }

    public Integer getSex() {
        return sex == null ? 0 : sex;
    }

    public Date getBirthday() {
        return birthday == null ? new Date() : birthday;
    }

    public Integer getCurrentIntegral() {
        return currentIntegral == null ? 0 : currentIntegral;
    }

    public Integer getCoupon() {
        return coupon == null ? 0 : coupon;
    }
}
