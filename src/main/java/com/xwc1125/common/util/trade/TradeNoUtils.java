package com.xwc1125.common.util.trade;

import com.xwc1125.common.util.date.DateFormat;
import com.xwc1125.common.util.date.DateUtils;
import com.xwc1125.common.util.string.StringUtils;
import com.xwc1125.common.util.uuid.RandomUtils;

import java.util.Date;

/**
 * <p>
 * Description:
 * </p>
 * <p>
 *
 * </p>
 *
 * @author xwc1125
 * @date 2017年8月25日 下午4:26:20
 */
public class TradeNoUtils {

    // 防止创建类的实例
    private TradeNoUtils() {
    }

    private static Object locker;
    private static Object oldLocker = DateUtils.FormatDate(new Date(), DateFormat.YYYYMMddHHmmss.getValue());

    private static long sn = 0;

    /**
     * <p>
     * Description: 账单号
     * </p>
     * <p>
     *
     * </p>
     *
     * @return
     * @author xwc1125
     * @date 2017年8月25日 下午5:00:50
     */
    public static String NextBillNumber() {
        // 每分钟的最大生产量为9999999999L
        locker = DateUtils.FormatDate(new Date(), "yyyyMMddHHmm");
        if (!oldLocker.equals(locker)) {
            sn = 0;
            oldLocker = locker;
        }
        synchronized (locker) {
            if (sn == 9999999999L) {
                sn = 0;
            } else {
                sn++;
            }
            //年月日时分秒微秒+随机码(2)+流水号+随机码(3)
            return DateUtils.FormatDate(new Date(), DateFormat.YYYYMMddHHmmss.getValue()) + RandomUtils.generateNumber(2)
                    + StringUtils.addZeroForNum(sn + "", 10, true) + RandomUtils.generateNumber(3);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(NextBillNumber());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
