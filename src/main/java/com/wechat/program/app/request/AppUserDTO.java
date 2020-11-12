package com.wechat.program.app.request;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

@Data
public class AppUserDTO {

    private Integer id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否会员， 默认非会员(false)， 会员(true)
     */
    private Boolean member = false;

    /**
     * 密码
     */
    private String password;

    /**
     * 类型：0:客户，1：员工，2：管理员'
     */
    private Integer type;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 套餐Id
     */
    private Integer comboId;

    /**
     * 之前套餐的id， 这个字段用于修改会员时，可能会修改套餐
     */
    private Integer preComboId;

    /**
     * 赠送时长
     */
    private Integer presentTime = 0;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 当前积分
     */
    private Integer currentIntegral;

    /**
     * 优惠卷
     */
    private Integer coupon;


    /**
     * 卷有效期
     */
    private Date validityVolume;

    public String getPassword() {
        return StringUtils.isEmpty(password)? "123456" : password;
    }


    public Integer getPresentTime() {
        return presentTime == null ? 0 : presentTime;
    }
}
