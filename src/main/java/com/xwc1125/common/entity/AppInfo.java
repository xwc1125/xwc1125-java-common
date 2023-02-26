package com.xwc1125.common.entity;

import com.xwc1125.common.util.json.JsonUtils;

/**
 * app相关信息
 *
 * @author xwc1125
 * @date 2016年8月17日 下午5:45:58
 */
public class AppInfo implements java.io.Serializable {

    /**
     * app名称
     */
    private String l;
    /**
     * app版本号
     */
    private int c;
    /**
     * app版本名称
     */
    private String v;
    /**
     * app包名
     */
    private String pk;
    /**
     * app密钥
     */
    private String apk;
    /**
     * company密钥;
     */
    private String cpk;
    /**
     * 平台
     */
    private String platform;
    /**
     * 包签名内容
     */
    private String sign;
    /**
     * 随机码;
     */
    private Long r;

    public AppInfo() {
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getApk() {
        return apk;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }

    public String getCpk() {
        return cpk;
    }

    public void setCpk(String cpk) {
        this.cpk = cpk;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getR() {
        return r;
    }

    public void setR(Long r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
