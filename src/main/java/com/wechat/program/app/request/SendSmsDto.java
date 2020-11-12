package com.wechat.program.app.request;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SendSmsDto implements Serializable {
    private static final long serialVersionUID = 68620056131350971L;

    public SendSmsDto(String mobile, int type) {
        this.mobile = mobile;
        this.type = type;
    }

    public SendSmsDto() {
    }

    private String mobile;
    private int type;

    private ArrayList<String> params = new ArrayList<>();
}
