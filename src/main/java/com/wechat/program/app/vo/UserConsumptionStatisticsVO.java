package com.wechat.program.app.vo;

import com.wechat.program.app.entity.AppCombo;
import lombok.Data;

import java.util.List;

@Data
public class UserConsumptionStatisticsVO {

    // 用户id
    private Integer id;

    private String name;

    private String phone;

    /**
     * 赠送时长
     */
    private Integer presentTime = 0;

    private List<AppCombo> comboList;
}
