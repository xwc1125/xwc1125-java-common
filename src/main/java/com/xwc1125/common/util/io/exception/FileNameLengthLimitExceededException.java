package com.xwc1125.common.util.io.exception;

/**
 * 文件名称超长限制异常类
 * @Author: xwc1125
 * @Date: 2019-03-09 22:08:38
 * @Copyright Copyright@2019
 */
public class FileNameLengthLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[]{defaultFileNameLength});
    }
}
