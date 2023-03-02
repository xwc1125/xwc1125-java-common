package com.xwc1125.common.security.xss;

import org.jsoup.Jsoup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.jsoup.safety.Safelist;

/**
 * @Description: XSS过滤处理
 * @Author: xwc1125
 * @Date: 2019-03-09 19:53
 * @Copyright Copyright@2019
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                // 防xss攻击和过滤前后空格
                escapseValues[i] = Jsoup.clean(values[i], Safelist.relaxed()).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }
}
