package com.xwc1125.common.util.ip;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-05-14 18:38
 * @Copyright Copyright@2019
 */
public class SocketUtils {

    private static Logger log = LoggerFactory.getLogger(SocketUtils.class);

    /**
     * Description: ip+port是否可连接
     * </p>
     *
     * @param host
     * @param port
     * @return boolean
     * @Author: xwc1125
     * @Date: 2019-05-14 18:38:51
     */
    public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return true;
    }

    /**
     * Description: host是否可到达
     * </p>
     *
     * @param host
     * @param timeOut
     * @return boolean
     * @Author: xwc1125
     * @Date: 2019-05-14 18:39:06
     */
    public static boolean isHostReachable(String host, Integer timeOut) {
        try {
            return InetAddress.getByName(host).isReachable(timeOut);
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
