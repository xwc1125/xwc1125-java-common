package com.xwc1125.common.util.sql;

import com.xwc1125.common.constant.StatusType;
import com.xwc1125.common.entity.TcpInfo;
import com.xwc1125.common.util.ip.TcpUtils;
import com.xwc1125.common.util.reflect.ReflectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Date;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-03-08 10:29
 * @Copyright Copyright@2019
 */
public class EntityUtils {
    /**
     * Description: 默认设置createName,createUserId,createHost,createTime,
     * updateName,updateUserId,updateHost,updateTime
     * </p>
     *
     * @param entity
     * @return void
     * @Author: xwc1125
     * @Date: 2019-03-08 10:50:37
     */
    public static <T> void setCreatAndUpdatInfo(T entity) {
        setCreateInfo(entity);
        setUpdatedInfo(entity);
    }

    /**
     * Description: 默认设置createName,createUserId,createHost,createTime,status
     *
     * </p>
     *
     * @param entity
     * @return void
     * @Author: xwc1125
     * @Date: 2019-03-08 10:51:58
     */
    public static <T> void setCreateInfo(T entity) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String hostIp = "";
        String name = "";
        String id = "";
        TcpInfo tcpInfo = null;
        Integer status = StatusType.OK.value;
        if (request != null) {
            tcpInfo = TcpUtils.getTCPInfo(request, false);
            hostIp = tcpInfo.getIp();
            name = String.valueOf(request.getHeader("userName"));
            name = URLDecoder.decode(name);
            id = String.valueOf(request.getHeader("userId"));
        }
        // 默认属性
        String[] fields = {"createName", "createUserId", "createHost", "createTime", "status"};
        Field field = ReflectUtils.getAccessibleField(entity, "createTime");
        // 默认值
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{name, id, hostIp, new Date(), status};
        }
        // 填充默认属性值
        setDefaultValues(entity, fields, value);
    }

    /***
     * Description: 默认设置updateName,updateUserId,updateHost,updateTime
     * </p>
     * @param entity
     *
     * @return void
     * @Author: xwc1125
     * @Date: 2019-03-08 10:52:09
     */
    public static <T> void setUpdatedInfo(T entity) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String hostIp = "";
        String name = "";
        String id = "";
        if (request != null) {
            TcpInfo tcpInfo = null;
            tcpInfo = TcpUtils.getTCPInfo(request, false);
            hostIp = tcpInfo.getIp();
            name = String.valueOf(request.getHeader("userName"));
            name = URLDecoder.decode(name);
            id = String.valueOf(request.getHeader("userId"));
        }
        // 默认属性
        String[] fields = {"updateName", "updateUserId", "updateHost", "updateTime"};
        Field field = ReflectUtils.getAccessibleField(entity, "updateTime");
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{name, id, hostIp, new Date()};
        }
        // 填充默认属性值
        setDefaultValues(entity, fields, value);
    }

    /**
     * 依据对象的属性数组和值数组对对象的属性进行赋值
     *
     * @param entity 对象
     * @param fields 属性数组
     * @param value  值数组
     * @author 王浩彬
     */
    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (ReflectUtils.hasField(entity, field)) {
                ReflectUtils.invokeSetter(entity, field, value[i]);
            }
        }
    }

    /**
     * Description:  默认设置updateName,updateUserId,updateHost,updateTime,status
     * </p>
     *
     * @param entity
     * @return void
     * @Author: xwc1125
     * @Date: 2019-03-08 10:58:43
     */
    public static <T> void setDeleteInfo(T entity) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String hostIp = "";
        String name = "";
        String id = "";
        Integer status = StatusType.DELETE.value;
        if (request != null) {
            TcpInfo tcpInfo = null;
            tcpInfo = TcpUtils.getTCPInfo(request, false);
            hostIp = tcpInfo.getIp();
            name = String.valueOf(request.getHeader("userName"));
            name = URLDecoder.decode(name);
            id = String.valueOf(request.getHeader("userId"));
        }
        // 默认属性
        String[] fields = {"updateName", "updateUserId", "updateHost", "updateTime", "status"};
        Field field = ReflectUtils.getAccessibleField(entity, "updateTime");
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{name, id, hostIp, new Date(), status};
        }
        // 填充默认属性值
        setDefaultValues(entity, fields, value);
    }
}
