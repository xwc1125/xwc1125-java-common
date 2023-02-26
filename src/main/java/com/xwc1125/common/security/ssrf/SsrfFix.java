package com.xwc1125.common.security.ssrf;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * description:
 * </p>
 *
 * @author: xwc1125
 * @date: 2023-02-13 21:27:19
 * @copyright Copyright@2023
 */
public class SsrfFix {

    /**
     * 初始化
     *
     * @throws IOException
     */
    public static void patchInit() throws IOException {
        SocketSecFactory.init();
        try {
            Socket.setSocketImplFactory(new SocketSecFactory());
        } catch (SocketException ignored) {
        }
    }

    /**
     * 初始化
     *
     * @param whitelist 白名单
     * @param blackSubnets 黑名单的子网
     * @throws IOException
     */
    public static void patchInit(ArrayList<String> whitelist, ArrayList<String> blackSubnets) throws IOException {
        SocketSecFactory.whitelist = whitelist;
        SocketSecFactory.addBlackSubnets(blackSubnets);
        SocketSecFactory.init();
        try {
            Socket.setSocketImplFactory(new SocketSecFactory());
        } catch (SocketException ignored) {
        }
    }

    /**
     * 匹配
     */
    public static void patch() {
        InterceptFlag.ThreadSsrfInterceptFlag.set(Thread.currentThread().getName());
    }

    /**
     * 取消匹配
     */
    public static void unpatch() {
        InterceptFlag.ThreadSsrfInterceptFlag.remove();
    }
}
