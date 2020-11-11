package com.wechat.program.app.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AppDeskVo {

    private Integer id;

    private Integer deskId;

    private Integer userId;

    private String userInfo;

    /**
     * 消费时长
     */
    private Integer consumptionTime;

    /** 0:暂停，1：使用，2：结束'*/
    private Integer status;

    /**
     * 剩余时间
     */
    private Integer remainingTime;

    private Boolean used;

    /**
     * 计时时间，
     * 1、每次由开始到暂停，需要计算出之前的消费时长，
     * 2、又暂停到开始，需要重新记录计时时间
     */
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date recordTime;
}
