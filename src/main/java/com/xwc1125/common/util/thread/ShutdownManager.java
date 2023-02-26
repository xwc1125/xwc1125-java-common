package com.xwc1125.common.util.thread;

import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 确保应用退出时能关闭后台线程
 * @Author: xwc1125
 * @Date: 2019-03-09 21:39
 * @Copyright Copyright@2019
 */
@Component
public class ShutdownManager {

    private static Logger log = LoggerFactory.getLogger(ShutdownManager.class);
    @PreDestroy
    public void destroy() {
        shutdownSpringSessionValidationScheduler();
        shutdownAsyncManager();
    }

    /**
     * 停止Session会话检查
     */
    private void shutdownSpringSessionValidationScheduler() {
        log.info("====关闭会话验证任务====");
    }

    /**
     * 停止异步执行任务
     */
    private void shutdownAsyncManager() {
        try {
            log.info("====关闭后台任务任务线程池====");
            AsyncManager.getInstance().shutdown();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
