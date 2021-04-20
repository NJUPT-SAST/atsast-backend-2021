package com.sast.atSast.exception;

import com.sast.atSast.enums.CustomError;

/**
 * @Author: Xander
 * @Date: 2021/4/20 14:42
 * @Description: 运行产生的错误由LocalRuntimeException抛出
 **/
public class LocalRuntimeException extends RuntimeException {
    private CustomError error;

    public LocalRuntimeException(CustomError error) {
        this.error = error;
    }

    public LocalRuntimeException(CustomError error, String message) {
        super(message);
        this.error = error;
    }

    public CustomError getError() {
        return error;
    }

    public LocalRuntimeException(String message, Throwable cause, CustomError error) {
        super(message, cause);
        this.error = error;
    }
}
