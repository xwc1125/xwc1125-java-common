package com.xwc1125.common.security.ssrf;

import org.junit.Test;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2023/2/13 21:37
 * @Copyright Copyright@2023
 */
public class UrlCheckerTest {

    @Test
    public void isSafe() {
        UrlChecker ck = new UrlChecker();
        ck.addHosts(".*xwc1125.com");
        System.out.println(ck.isSafe("http://api.xwc1125.com/ssrf/xxx?a=b", 2));
    }
}
