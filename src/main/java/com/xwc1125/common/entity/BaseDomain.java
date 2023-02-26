package com.xwc1125.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xwc1125.common.util.date.DateUtils;
import com.xwc1125.common.util.json.LongToDateStrConverter;
import com.xwc1125.common.util.json.StrToIntegerConverter;
import com.xwc1125.common.util.json.StrToLongConverter;

import java.io.Serializable;

/**
 * Domain的基础类
 * app相关信息
 *
 * @author xwc1125
 * @date 2016年8月17日 下午5:45:58
 */
public abstract class BaseDomain implements Serializable {

    private static final long serialVersionUID = -2204422504655065037L;

    /**
     * 创建时间(毫秒)
     */
    @JsonDeserialize(converter = StrToLongConverter.class)
    @JsonSerialize(converter = LongToDateStrConverter.class)
    public Long createTime = DateUtils.CurrentMilliseconds();
    /**
     * 更新时间(毫秒)
     */
    @JsonDeserialize(converter = StrToLongConverter.class)
    @JsonSerialize(converter = LongToDateStrConverter.class)
    public Long updateTime = DateUtils.CurrentMilliseconds();
    /**
     * 状态[0正常，1禁用，2删除]
     */
    @JsonDeserialize(converter = StrToIntegerConverter.class)
    public Integer status = 0;

    /**
     * 本地状态，用于数据同步更新 [0 已同步，1 未同步]
     */
    @JsonIgnore
    @JsonDeserialize(converter = StrToIntegerConverter.class)
    public Integer localState;

    public BaseDomain() {
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLocalState() {
        return localState;
    }

    public void setLocalState(Integer localState) {
        this.localState = localState;
    }
}
