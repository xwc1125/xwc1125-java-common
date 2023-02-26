package com.xwc1125.common.security.ssrf;

/**
 * description:
 * </p>
 *
 * @author: xwc1125
 * @date: 2023-02-13 21:27:14
 * @copyright Copyright@2023
 */
public class SsrfException extends RuntimeException {

    SsrfException(String s) {
        super(s);
    }

}
