package com.xwc1125.common.security.ssrf;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * description: url检测
 * </p>
 *
 * @author: xwc1125
 * @date: 2023-02-13 21:27:24
 * @copyright Copyright@2023
 */
public class UrlChecker {

    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";
    private ArrayList<String> schemeArrs = new ArrayList<String>();
    private ArrayList<String> hostList = new ArrayList<String>();

    public UrlChecker() {
        this.schemeArrs.add(SCHEME_HTTP);
        this.schemeArrs.add(SCHEME_HTTPS);
    }

    public UrlChecker(ArrayList<String> hostList) {
        this.hostList = hostList;
        this.schemeArrs.add(SCHEME_HTTP);
        this.schemeArrs.add(SCHEME_HTTPS);
    }

    public UrlChecker(ArrayList<String> schemeArrs, ArrayList<String> hostList) {
        if (schemeArrs != null) {
            this.schemeArrs = schemeArrs;
        }
        if (hostList != null) {
            this.hostList = hostList;
        }
        if (!this.schemeArrs.contains(SCHEME_HTTP)) {
            this.schemeArrs.add(SCHEME_HTTP);
        }
        if (!this.schemeArrs.contains(SCHEME_HTTPS)) {
            this.schemeArrs.add(SCHEME_HTTPS);
        }
    }

    /**
     * 添加host
     *
     * @param host
     */
    public void addHosts(String host) {
        this.hostList.add(host);
    }

    /**
     * 添加scheme
     *
     * @param host
     */
    public void addSchemeArrs(String host) {
        this.schemeArrs.add(host);
    }

    /**
     * 判断url是否安全
     * 1、scheme是否支持
     * 2、host是否匹配
     * - type:0 全字符串匹配;1 匹配子域名;2 正则匹配;
     *
     * @param url
     * @param type
     * @return
     */
    public boolean isSafe(String url, int type) {
        try {
            // 排除\和#对域名判断的干扰
            url = url.replaceAll("[\\\\#]", "/");
            URL thisurl = new URL(url);
            if (thisurl.getHost().length() < 1) {
                return false;
            }
            ArrayList<String> schemeArrs = lowercase(this.schemeArrs);
            ArrayList<String> hostList = lowercase(this.hostList);
            if (!schemeArrs.contains(thisurl.getProtocol().toLowerCase())) {
                return false;
            }
            if (this.hostList.size() == 0) {
                return true;
            }
            boolean flag = false;
            switch (type) {
                // 全字符串匹配
                case 0:
                    if (hostList.contains(thisurl.getHost().toLowerCase())) {
                        flag = true;
                    }
                    return flag;
                // 匹配子域名
                case 1:
                    for (String host : hostList) {
                        if (thisurl.getHost().toLowerCase().endsWith(host)) {
                            flag = true;
                        }
                    }
                    return flag;
                // 正则匹配
                case 2:
                    for (String host : hostList) {
                        try {
                            if (Pattern.matches(host, thisurl.getHost().toLowerCase())) {
                                flag = true;
                            }
                        } catch (PatternSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    return flag;
                default:
                    return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ArrayList<String> lowercase(ArrayList<String> ori) {
        ArrayList<String> lower = new ArrayList<String>();
        for (String str : ori) {
            lower.add(str.toLowerCase());
        }
        return lower;
    }

}
