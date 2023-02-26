package com.xwc1125.common.entity;

import java.io.Serializable;

/**
 *
 * @author xwc1125
 * @date 2016年8月17日 下午5:45:58
 */
public class BaseVo implements Serializable {

    /**
     * 创建时间
     */
    protected Long createTime;
    /**
     * 更新时间
     */
    protected Long updateTime;
    /**
     * 状态[0正常，1禁用，2删除]
     */
    protected Integer status;

    protected Integer localState;

    public BaseVo() {
    }

    protected BaseVo(BaseDomain domain) {
        this.createTime = domain.getCreateTime();
        this.updateTime = domain.getUpdateTime();
        this.status = domain.getStatus();
        this.localState = domain.getLocalState();
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
