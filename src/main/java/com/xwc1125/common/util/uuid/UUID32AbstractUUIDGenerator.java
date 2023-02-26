/**
 *  @Project ZzxApp-1.0 
 *  @Package com.zzx.framework.util.uuid
 *
 * @Title AbstractUUIDGenerator.java
 * @Description
 * @Copyright Copyright (c) 2016
 * @author xwc1125
 * @date 2016年2月8日 下午9:12:35 
 * @version V1.0
 */
package com.xwc1125.common.util.uuid;

import java.net.InetAddress;

/**
 * @ClassName AbstractUUIDGenerator
 * @Description 抽象的uuid生成类
 * @author xwc1125
 * @date 2016年2月8日 下午9:12:35
 */
abstract class UUID32AbstractUUIDGenerator {
    private static final int IP;

    static {
        int ipadd;
        try {
            ipadd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (final Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    public static int toInt(final byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + bytes[i];
        }
        return result;
    }

    private static short counter = (short) 0;
    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

    public UUID32AbstractUUIDGenerator() {
    }

    /**
     * Unique across JVMs on this machine (unless they load this class in the
     * same quater second - very unlikely)
     */
    protected int getJVM() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there are >
     * Short.MAX_VALUE instances created in a millisecond)
     */
    protected short getCount() {
        synchronized (UUID32AbstractUUIDGenerator.class) {
            if (counter < 0)
                counter = 0;
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    protected int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    protected short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected int getLoTime() {
        return (int) System.currentTimeMillis();
    }
}
