package com.wechat.program.app.service.impl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.wechat.program.app.constant.Constants;
import com.wechat.program.app.exception.ProgramException;
import com.wechat.program.app.request.SendSmsDto;
import com.wechat.program.app.request.VerifyCodeDto;
import com.wechat.program.app.service.SmsService;
import com.wechat.program.app.utils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.wechat.program.app.constant.Constants.SIGN;

/**
 * @auther 王军伟
 * @create 2019-10-13 09:15
 * @desc
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsSingleSender smsSingleSender;

    /**
     * 短信模板ID
     **/
    @Value("${smsconfig.templateid}")
    private int TEMPLATE_ID;

//    /**
//     * 短信签名内容
//     */
//    @Value("${smsconfig.smsSign}")
//    private String SMS_SIGN;

//
//    @Value("${smsconfig.invalidtime}")
//    private String INVALID_TIME;

//    @Value("${smsconfig.phone-prefix}")
//    private String PHONE_PREFIX;

    @Override
    public void sendSmsCode(SendSmsDto sendSmsDto) {
        // 可以在发送验证码之前对当前手机号做一些验证，如该手机号是否注册过
        // 为了防止短信轰炸可以根据业务需求在此做一些限制
        String code = SmsUtil.getCode();
        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(code);
//            params.add(INVALID_TIME);
//            String sign = new String(SMS_SIGN, "utf-8");
            SmsSingleSenderResult senderResult = smsSingleSender
                    .sendWithParam("86", sendSmsDto.getMobile(),
                            TEMPLATE_ID, params, SIGN, "", "");
            int statusCode = senderResult.result;
            String statusMsg = senderResult.errMsg;
            log.info("发送短信，腾讯云返回状态码：" + statusCode);
            log.info("发送短信，腾讯云返回信息：" + statusMsg);
            // 过于频繁
            if (Constants.SEND_SMS_CODE_FREQUENT == statusCode) {
                throw new ProgramException("短信过于频繁！");
            }
            // 达到上限
            if (Constants.SEND_SMS_CODE_UPPER == statusCode) {
                throw new ProgramException("短信达到上限");
            }
            if (Constants.SEND_SMS_CODE_SUCCESS != statusCode) {
                throw new ProgramException("短信发送错误");
            }

        } catch (Exception e) {
            throw new ProgramException("短信发送错误");
        }
    }

    @Override
    public void verifyCode(VerifyCodeDto verifyCodeDto) {
//        String redisCode = (String) redisUtil.getCacheObject(SmsUtil.map
//                .get(verifyCodeDto.getType()) + verifyCodeDto.getMobile());
//        AssertUtil.isNull(redisCode, R.USER_LOGIN_ERROR4);
//        if (redisCode.equals(verifyCodeDto.getCode())) {
//            redisUtil.delete(SmsUtil.map.get(verifyCodeDto.getType()) + verifyCodeDto.getMobile());
//        } else {
//            throw new ProgramException(R.USER_CODE_ERROR, "");
//        }
    }
}
