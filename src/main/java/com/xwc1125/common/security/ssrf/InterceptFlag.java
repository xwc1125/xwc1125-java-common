package com.xwc1125.common.security.ssrf;

/**
 * description:
 * </p>
 * @author: xwc1125
 * @date: 2023-02-13 21:26:36
 * @copyright Copyright@2023
 */
public class InterceptFlag {
    public static ThreadLocal<String> ThreadSsrfInterceptFlag = new ThreadLocal<>();

}
