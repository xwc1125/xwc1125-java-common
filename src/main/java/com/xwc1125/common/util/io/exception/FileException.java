package com.xwc1125.common.util.io.exception;

import com.xwc1125.common.exception.BaseException;

/**
 * 文件信息异常类
 * @Author: xwc1125
 * @Date: 2019-03-09 22:08:38
 * @Copyright Copyright@2019
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
