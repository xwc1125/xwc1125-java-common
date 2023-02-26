package com.xwc1125.common.entity;

import java.io.Serializable;

/**
 * @author xwc1125
 * @ClassName TcpInfo
 * @Description 接口访问的相关数据
 * @date 2016年2月26日 上午10:57:39
 */
public class TcpInfo implements Serializable {

    private String ip;
    private String ipAddress;
    private String url;
    private String api;
    private String ua;
    private String browser;
    private String os;

    public TcpInfo() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
