package com.xwc1125.common.constant;

/**
 * @Description: 支付类型【0：限制性免费，1：预付费，2：后付费，3：白名单免费】
 * @Author: xwc1125
 * @Date: 2019-02-21 14:30
 * @Copyright Copyright@2019
 */
public enum PayType {
    /**
     * 0：限制性免费
     */
    RESTRICTIVE(0, "RESTRICTIVE"),
    /**
     * 1：预付费
     */
    PREPAID(1, "PREPAID"),
    /**
     * 2：后付费
     */
    POSTPAID(2, "POSTPAID"),
    /**
     * 3：白名单免费
     */
    WHITElIST(3, "WHITElIST");

    public final int value;
    public final String msg;

    PayType(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    /**
     * @param @param  payType支付类型【0：限制性免费，1：预付费，2：后付费，3：白名单免费】
     * @param @return
     * @return PAY_TYPE
     * @Title getPayType
     * @Description 通过int转换为PAY_TYPE
     * @author xwc1125
     * @date 2016年3月3日 上午11:33:16
     */
    public static PayType getPayType(int payType) {
        PayType pType = PayType.PREPAID;
        switch (payType) {
            case 0:
                pType = PayType.RESTRICTIVE;
                break;
            case 1:
                pType = PayType.PREPAID;
                break;
            case 2:
                pType = PayType.POSTPAID;
                break;
            case 3:
                pType = PayType.WHITElIST;
                break;
            default:
                break;
        }
        return pType;
    }
}
