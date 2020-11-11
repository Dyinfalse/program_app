package com.wechat.program.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Table(name = "app_desk_user")
public class AppDeskUser extends BaseEntity {

    private Integer deskId;

    private Integer userId;

    private Integer consumptionTime = 0;

    /** 0:暂停，1：使用，2：结束'*/
    private Integer status;


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

    @Transient
    private Integer remainingTime;
}
