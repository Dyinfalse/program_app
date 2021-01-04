package com.wechat.program.app.request;

import lombok.Data;

@Data
public class AppUserTotalTimeDTO {
    // 会员id
    private Integer id;

    private Integer totalTime;
}
