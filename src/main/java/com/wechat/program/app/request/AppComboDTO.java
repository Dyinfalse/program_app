package com.wechat.program.app.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppComboDTO {

    private String name;

    private BigDecimal amount;

    private Integer duration;
}
