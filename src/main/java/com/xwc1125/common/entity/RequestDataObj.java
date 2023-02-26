package com.xwc1125.common.entity;

import com.xwc1125.common.constant.ClientOsType;

import java.util.Map;

/**
 * @Description:RequestDataObj
 * @author xwc1125
 * @Date: 2019-03-29 15:29
 * @Copyright Copyright@2019
 */
public class RequestDataObj {

    private TcpInfo tcpInfo;
    private AppInfo app;
    private SdkInfo sdk;
    private PhoneInfo phone;
    private DeviceInfo device;
    private ClientInfo client;
    private CoreDataInfo data;
    private Map treeMap;
    private ClientOsType osType;
    private String aesKey;

    public RequestDataObj() {
    }

    public void setTcpInfo(TcpInfo tcpInfo) {
        this.tcpInfo = tcpInfo;
    }

    public void setApp(AppInfo app) {
        this.app = app;
    }

    public void setSdk(SdkInfo sdk) {
        this.sdk = sdk;
    }

    public void setPhone(PhoneInfo phone) {
        this.phone = phone;
    }

    public void setDevice(DeviceInfo device) {
        this.device = device;
    }

    public void setClient(ClientInfo client) {
        this.client = client;
    }

    public void setData(CoreDataInfo data) {
        this.data = data;
    }

    public void setTreeMap(Map treeMap) {
        this.treeMap = treeMap;
    }

    public void setOsType(ClientOsType osType) {
        this.osType = osType;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public TcpInfo getTcpInfo() {
        return tcpInfo;
    }

    public Map getTreeMap() {
        return treeMap;
    }

    public ClientOsType getOsType() {
        return osType;
    }

    public String getAesKey() {
        return aesKey;
    }

    public AppInfo getApp() {
        return app;
    }

    public SdkInfo getSdk() {
        return sdk;
    }

    public PhoneInfo getPhone() {
        return phone;
    }

    public DeviceInfo getDevice() {
        return device;
    }

    public ClientInfo getClient() {
        return client;
    }

    public CoreDataInfo getData() {
        return data;
    }

    public CoreDataInfo getCoreDataInfo() {
        return data;
    }

}
