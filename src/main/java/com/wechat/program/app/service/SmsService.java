package com.wechat.program.app.service;

import com.wechat.program.app.request.SendSmsDto;
import com.wechat.program.app.request.VerifyCodeDto;

public interface SmsService {
    void sendSmsCode(SendSmsDto sendSmsDto);

    void verifyCode(VerifyCodeDto verifyCodeDto);
}
