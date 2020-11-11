package com.wechat.program.app.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeDto implements Serializable {
    private static final long serialVersionUID = -930643320039975742L;

    private String mobile;
    private String code;
    private int type;
}
