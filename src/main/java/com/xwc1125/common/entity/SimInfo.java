package com.xwc1125.common.entity;

import com.xwc1125.common.util.json.JsonUtils;

/**
 * sim卡信息
 *
 * @author xwc1125
 * @date 2016年3月18日下午3:07:38
 */
public class SimInfo implements java.io.Serializable {

    /**
     * imsi
     */
    private String is;
    /**
     * iccid
     */
    private String ic;
    /**
     * mobile
     */
    private String m;
    /**
     * CarrierName -中国联通 4G
     */
    private String cn;
    /**
     * 从0开始，最大为卡槽数。 0 即表示卡1的数据
     */
    private int sid;
    /**
     * 类别 0:高通、联发科反射数据 1:兼容的4.0，5.0的反射数据 2:5.0以上的反射数据
     */
    private int t;
    /**
     * 移动网络是否首选该卡
     */
    private boolean idfd;
    /**
     * 短信是否首选该卡
     */
    private boolean idfs;
    /**
     * 随机码;
     */
    private Long r;

    public SimInfo() {
    }

    public String getIs() {
        return is;
    }

    public void setIs(String is) {
        this.is = is;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public boolean isIdfd() {
        return idfd;
    }

    public void setIdfd(boolean idfd) {
        this.idfd = idfd;
    }

    public boolean isIdfs() {
        return idfs;
    }

    public void setIdfs(boolean idfs) {
        this.idfs = idfs;
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
