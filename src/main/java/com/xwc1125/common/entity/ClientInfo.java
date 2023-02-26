package com.xwc1125.common.entity;

import com.xwc1125.common.util.json.JsonUtils;

/**
 * ClientInfo
 *
 * @author xwc1125
 * @date 2016年7月8日 下午3:56:43
 */
@SuppressWarnings("serial")
public class ClientInfo implements java.io.Serializable {

    private String clientId;
    private String clientSecret;
    private String aesKey;
    private Long r;

    public ClientInfo() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
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
