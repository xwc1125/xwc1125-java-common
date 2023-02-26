package com.xwc1125.common.exception;

import com.xwc1125.common.util.i18n.MessageUtils;
import com.xwc1125.common.util.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 基础异常
 * </p>
 *
 * @Author: xwc1125
 * @Date: 2019-03-09 22:14:21
 * @Copyright Copyright@2019
 */
public class BaseException extends RuntimeException {

    private static Logger log = LoggerFactory.getLogger(BaseException.class);

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public BaseException(String module, String code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public BaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BaseException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public BaseException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        BaseException.log = log;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(code)) {
            try {
                message = MessageUtils.message(code, args);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }

}
