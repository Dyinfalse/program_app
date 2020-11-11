package com.wechat.program.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wechat.program.app.core.IEntity;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class BaseEntity implements IEntity {

    private static final long serialVersionUID = -2975424897106620060L;
    @Id
    @GeneratedValue(
            generator = "JDBC"
    )
    protected Integer id;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    protected Date createTime;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    protected Date updateTime;

    public BaseEntity() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime == null ? new Date() : createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime == null ? new Date() : updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
