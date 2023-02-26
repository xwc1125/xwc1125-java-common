package com.xwc1125.common.entity;

import com.xwc1125.common.util.json.JsonUtils;

/**
 * apk的内容
 *
 * @author xwc1125
 * @date 2016年8月17日 下午5:45:58
 */
public class ApkInfo {

    /**
     * App的包名
     */
    private String pkName;
    /**
     * App名称
     */
    private String label;
    /**
     * App版本
     */
    private String versionName;
    /**
     * App版本号
     */
    private int versionCode;
    /**
     * App的位置
     */
    private String apkfile;
    /**
     * 0:未安装，1:已安装
     */
    private int isInstalled = 0;
    /**
     * -1:未知，0：失败，1：成功，2：升级失败，3：升级成功
     */
    private int isSuccInstall = -1;

    public ApkInfo() {
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getApkfile() {
        return apkfile;
    }

    public void setApkfile(String apkfile) {
        this.apkfile = apkfile;
    }

    public int getIsInstalled() {
        return isInstalled;
    }

    public void setIsInstalled(int isInstalled) {
        this.isInstalled = isInstalled;
    }

    public int getIsSuccInstall() {
        return isSuccInstall;
    }

    public void setIsSuccInstall(int isSuccInstall) {
        this.isSuccInstall = isSuccInstall;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
