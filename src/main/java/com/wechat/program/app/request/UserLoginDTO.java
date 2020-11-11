package com.wechat.program.app.request;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String phone;

    private String password;
}
