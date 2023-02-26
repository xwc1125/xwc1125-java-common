package com.xwc1125.common.entity;

import com.xwc1125.common.util.json.JsonUtils;

/**
 * DeviceInfo3
 *
 * @author xwc1125
 * @date 2016年4月25日 下午3:05:21
 */
public class DeviceInfo implements java.io.Serializable {

    /**
     * 唯一码
     */
    private String uid;
    /**
     * 设备名称deviceName
     */
    private String dn;
    /**
     * 设备系统名称deviceSystemName
     */
    private String dsn;
    /**
     * 设备系统版本名称DeviceSystemVersion
     */
    private String dsv;
    /**
     * 设备model
     */
    private String dm;
    /**
     * 设备localizeModel
     */
    private String dlm;
    /**
     * 随机码
     */
    private String r;

    public DeviceInfo() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getDsn() {
        return dsn;
    }

    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    public String getDsv() {
        return dsv;
    }

    public void setDsv(String dsv) {
        this.dsv = dsv;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getDlm() {
        return dlm;
    }

    public void setDlm(String dlm) {
        this.dlm = dlm;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
