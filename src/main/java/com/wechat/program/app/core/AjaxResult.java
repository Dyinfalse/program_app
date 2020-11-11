package com.wechat.program.app.core;

import com.alibaba.fastjson.JSONObject;

public class AjaxResult<T> {
    private String code;
    private String message;
    private T data;

    protected AjaxResult(String code, T data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    protected AjaxResult() {
    }

    public T getData() {
        return this.data;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static final <T> AjaxResult success() {
        return new AjaxResult("200", (Object)null, (String)null);
    }

    public static final <T> AjaxResult success(Object data) {
        return new AjaxResult("200", data, (String)null);
    }

    public static final <T> AjaxResult success(Object data, String message) {
        return new AjaxResult("200", data, message);
    }

    public static final <T> AjaxResult success(T o, Long total) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        json.put("rows", o);
        return new AjaxResult("200", json, (String)null);
    }

    public static final <T> AjaxResult failed() {
        return new AjaxResult("500", (Object)null, "系统异常");
    }

    public static final <T> AjaxResult failed(String message) {
        return new AjaxResult("500", null, message);
    }

}
