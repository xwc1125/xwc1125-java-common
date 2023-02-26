package com.xwc1125.common.security.ssrf;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.SocketImplFactory;
import java.util.ArrayList;

/**
 * description:
 * </p>
 *
 * @author: xwc1125
 * @date: 2023-02-13 21:27:08
 * @copyright Copyright@2023
 */
public class SocketSecFactory implements SocketImplFactory {

    public static Constructor originConstructor = null;
    public static ArrayList<String> whitelist = null;
    private static ArrayList<String> blackSubnets = new ArrayList<>();

    static void init() {
        blackSubnets.add("10.255.255.255/8");
        blackSubnets.add("172.16.255.255/12");
        blackSubnets.add("192.168.0.0/16");
        blackSubnets.add("100.64.0.0/10");
        blackSubnets.add("127.0.0.0/8");
        blackSubnets.add("11.0.0.0/8");

        if (originConstructor != null) {
            return;
        }
        Socket socket = new Socket();
        try {
            Field implField = Socket.class.getDeclaredField("impl");
            implField.setAccessible(true);
            Class<?> clazz = implField.get(socket).getClass();
            SocketHookImpl.initSocketImpl(clazz);
            originConstructor = clazz.getDeclaredConstructor();
            originConstructor.setAccessible(true);

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException e) {
            throw new SsrfException("SocketSecFactory init failed");
        }

        try {
            socket.close();
        } catch (IOException ignored) {

        }
    }

    public static ArrayList<String> getBlackSubnets() {
        return blackSubnets;
    }

    public static void addBlackSubnets(ArrayList<String> blackSubnets) {
        if (blackSubnets == null || blackSubnets.size() == 0) {
            return;
        }
        SocketSecFactory.blackSubnets.addAll(blackSubnets);
    }

    public SocketImpl createSocketImpl() {
        if (InterceptFlag.ThreadSsrfInterceptFlag.get() != null
                && InterceptFlag.ThreadSsrfInterceptFlag.get().equals(Thread.currentThread().getName())) {
            try {
                return new SocketHookImpl(originConstructor);
            } catch (Exception e) {
                try {
                    return (SocketImpl) originConstructor.newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                }
            }
        } else {
            try {
                return (SocketImpl) originConstructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            }
        }

        return null;
    }
}
