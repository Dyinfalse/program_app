package com.wechat.program.app.constant;

public interface Constants {
    int SEND_SMS_CODE_SUCCESS = 0;
    int SEND_SMS_CODE_FREQUENT = 1023;
    int SEND_SMS_CODE_UPPER = 1025;
    String SEND_SMS_CODE_USER_REGISTER = "1100";
    String SEND_SMS_CODE_USER_LOGIN_ERROR = "1";
    int USER_CODE_TOOMANY = 1109;
    int OTHER_CODE_MAX_COUNT = 5;
    int UPDATE_PASSWORD_TYPE = 5;
    int UPDATE_MOBILE_TYPE = 4;
    int UPDATE_NEW_MOBILE_TYPE = 7;
    String VERIFY_CODE_INVALID = "invalid";
    String VERIFY_CODE_FAIL = "fail";

    /**
     * 短信签名
     */
    String SIGN = "咚咚积木";
}
