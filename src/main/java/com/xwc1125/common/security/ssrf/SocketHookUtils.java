package com.xwc1125.common.security.ssrf;

import java.lang.reflect.Method;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.util.SubnetUtils;

/**
 * description:
 * </p>
 *
 * @author: xwc1125
 * @date: 2023-02-13 21:27:01
 * @copyright Copyright@2023
 */
public class SocketHookUtils {

    static Method findMethod(Class<?> clazz, String findName, Class<?>[] args) {
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(findName, args);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    static boolean isInternalIp(String strIP, String host) {
        if (SocketSecFactory.whitelist != null) {
            for (String wip : SocketSecFactory.whitelist) {
                if (strIP.equals(wip) || host.equals(wip)) {
                    return false;
                }
            }
        }
        if (StringUtils.isEmpty(strIP)) {
            return false;
        }
        try {
            for (String subnet : SocketSecFactory.getBlackSubnets()) {
                SubnetUtils utils = new SubnetUtils(subnet);
                if (utils.getInfo().isInRange(strIP)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

}
