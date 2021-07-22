package com.sast.atSast.enums;

import lombok.AllArgsConstructor;

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
    UNKNOWN_ERROR(4004,"未知错误"),
    EXCEL_ERROR(4005,"Excel内容不符合要求"),
    RICH_TEXT_ERROR(4006,"富文本更新失败"),
    EMAIL_SENDING_ABNORMAL(4007,"邮箱发送错误"),
    WRONG_PASSWORD(4008,"密码错误"),
    UNKNOWN_ACCOUNT(4009,"用户名错误"),
    FAILED_VERIFICATION(4010,"验证未通过"),
    UN_LOGIN(4011,"未登录"),
    VERIFICATION_CODE_NOT_SENT(4012,"未发送验证码"),
    MINIO_ERROR(5000,"minio出现错误");


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
