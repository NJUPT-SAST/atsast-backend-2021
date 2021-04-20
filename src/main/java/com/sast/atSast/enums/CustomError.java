package com.sast.atSast.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

/**
 * @Author: Xander
 * @Date: 2021/4/20 14:21
 * @Description: 报错时抛出在这里定义的错误，全局错误处理会自动处理为HttpResponse并返回给前端
 **/
@AllArgsConstructor
public enum CustomError {
    INTERNAL_ERROR(5001,"内部错误"),
    REQUEST_ERROR(4001,"请求出错"),
    PERMISSION_DENY(4003,"权限错误"),
    UNKNOWN_ERROR(4004,"未知错误"),;


    private int code;

    private String errMsg;

    public int getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
