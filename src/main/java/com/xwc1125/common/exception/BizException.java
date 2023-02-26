package com.xwc1125.common.exception;

/**
 * 业务异常
 * @Author: xwc1125
 * @Date: 2019-03-09 22:14:21
 * @Copyright Copyright@2019
 */
public class BizException extends RuntimeException {

    protected final String message;

    public BizException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
