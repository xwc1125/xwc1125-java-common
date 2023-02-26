package com.xwc1125.common.util.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.log.NullLogChute;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

/**
 * @Description: Velocity工具类, 根据模板内容生成文件
 * @Author: xwc1125
 * @Date: 2021/1/5 10:18
 * @Copyright Copyright@2021
 */
public class VelocityUtils {

    static {
        // 禁止输出日志
        Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
        Velocity.init();
    }

    public static String generate(VelocityContext context, String template) {
        StringReader reader = new StringReader(template);
        StringWriter writer = new StringWriter();
        // 不用vm文件
        Velocity.evaluate(context, writer, "mystring", reader);

        try {
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

    /**
     * 初始化vm方法
     */
    public static void initVelocity() {
        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("file.resource.loader.class",
                    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.ENCODING_DEFAULT, "UTF8");
            p.setProperty(Velocity.OUTPUT_ENCODING, "UTF8");
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
