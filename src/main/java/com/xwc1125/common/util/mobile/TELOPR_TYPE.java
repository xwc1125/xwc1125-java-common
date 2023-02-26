package com.xwc1125.common.util.mobile;

import com.xwc1125.common.util.string.StringUtils;

/**
 * @author xwc1125
 * @ClassName TELOPR_TYPE
 * @Description 运营商类型：联通：CUCC，0；移动：CMCC，1；电信：CTC，2,UNKNOW:-1
 * @date 2016年3月7日 上午9:42:09
 */
public enum TELOPR_TYPE {
    /**
     * 联通：0
     */
    CUCC {
        @Override
        public String getValue() {
            return "CUCC";
        }

        @Override
        public int getIndex() {
            return 0;
        }
    },
    /**
     * 移动：1
     */
    CMCC {
        @Override
        public String getValue() {
            return "CMCC";
        }

        @Override
        public int getIndex() {
            return 1;
        }
    },
    /**
     * 电信：2
     */
    CTC {
        @Override
        public String getValue() {
            return "CTC";
        }

        @Override
        public int getIndex() {
            return 2;
        }
    },
    /**
     * 未知运营商：-1
     */
    UNKNOW {
        @Override
        public String getValue() {
            return "UNKNOW";
        }

        @Override
        public int getIndex() {
            return -1;
        }
    };

    public abstract String getValue();

    public abstract int getIndex();

    public static TELOPR_TYPE getValue(String telType) {
        TELOPR_TYPE teloprType = UNKNOW;
        if (StringUtils.isEmpty(telType)) {
            return teloprType;
        }
        String upTelType = telType.toUpperCase();
        if (upTelType.equals(CUCC.getValue())) {
            teloprType = CUCC;
        } else if (upTelType.equals(CMCC.getValue())) {
            teloprType = CMCC;
        } else if (upTelType.equals(CTC.getValue())) {
            teloprType = CTC;
        }
        return teloprType;
    }
}
