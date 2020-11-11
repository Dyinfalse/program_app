package com.wechat.program.app.entity;

import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "app_combo")
public class AppCombo extends BaseEntity {

    private String name;

    private BigDecimal amount;

    private Integer duration;
}
