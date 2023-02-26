package com.xwc1125.common.config;

import com.xwc1125.common.util.string.StringUtils;
import com.xwc1125.common.util.yml.YamlUtils;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2020/12/11 16:08
 * @Copyright Copyright@2020
 */
public class Global {

    private static Logger log = LoggerFactory.getLogger(Global.class);
    private static Global global = null;
    private static Map<String, String> map = new HashMap<String, String>();

    private Global() {
    }

    /**
     * 静态工厂方法 获取当前对象实例 多线程安全单例模式(使用双重同步锁)
     */
    public static synchronized Global getInstance() {
        if (global == null) {
            synchronized (Global.class) {
                if (global == null) {
                    global = new Global();
                }
            }
        }
        return global;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String configFile, String key) {
        String value = map.get(key);
        if (value == null) {
            Map<?, ?> yamlMap = null;
            try {
                yamlMap = YamlUtils.loadYaml(configFile);
                value = String.valueOf(YamlUtils.getProperty(yamlMap, key));
                if (StringUtils.isNotEmpty(value)) {
                    map.put(key, value);
                } else {
                    value = StringUtils.EMPTY;
                    map.put(key, StringUtils.EMPTY);
                }
            } catch (FileNotFoundException e) {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return value;
    }

    /**
     * 获取项目名称
     *
     * @return
     */
    public static String getName(String configFile) {
        return StringUtils.nvl(getConfig(configFile, "application.name"), "app");
    }

    /**
     * 获取项目版本
     *
     * @return
     */
    public static String getVersion(String configFile) {
        return StringUtils.nvl(getConfig(configFile, "application.version"), "v1.0.0");
    }
}
