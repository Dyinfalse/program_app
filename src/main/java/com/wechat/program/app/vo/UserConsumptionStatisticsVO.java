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

    private List<AppCombo> comboList;
}
