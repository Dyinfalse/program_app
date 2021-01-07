package com.wechat.program.app.config;

public class MessageSwitch {

    private static Boolean messageEnable = true;

    public static void setMessageEnable(Boolean messageEnable) {
        MessageSwitch.messageEnable = messageEnable;
    }

    public static Boolean getMessageEnable () {
        return MessageSwitch.messageEnable;
    }
}
