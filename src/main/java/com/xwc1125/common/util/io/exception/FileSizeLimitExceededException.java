package com.xwc1125.common.util.io.exception;

/**
 * 文件名大小限制异常类
 * @Author: xwc1125
 * @Date: 2019-03-09 22:08:38
 * @Copyright Copyright@2019
 */
public class FileSizeLimitExceededException extends FileException {

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }
}
