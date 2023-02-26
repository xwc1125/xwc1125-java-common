package com.xwc1125.common.util.method;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-02-21 10:10
 * @Copyright Copyright@2019
 */
public class MethodUtils {
    /**
     * <p>
     * Title: getMethodName
     * </p>
     * <p>
     * Description: 获取当前的函数名称
     * </p>
     * <p>
     *
     * </p>
     *
     * @return
     * @author xwc1125
     * @date 2015-7-14下午4:12:52
     */
    public static String getMethodName() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        String methodName = e.getMethodName();
        return methodName;
    }

    public static StackTraceElement getMethodPath() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        return e;
    }
}
