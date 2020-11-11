package com.wechat.program.app.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendSmsDto implements Serializable {
    private static final long serialVersionUID = 68620056131350971L;

    public SendSmsDto(String mobile) {
        this.mobile = mobile;
    }


    public SendSmsDto() {
    }

    private String mobile;
    private int type;
}
