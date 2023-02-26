package com.xwc1125.common.util.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xwc1125
 * @Date: 2019-03-09 21:39
 * @Copyright Copyright@2019
 */
public class SchedulerThreadHelper {

    /**
     * 线程池维护的最少线程数
     */
    private static int CORE_POOL_SIZE = 20;

    /**
     * 最大线程数
     */
    public static int MAX_POOL_SIZE = 30;

    /**
     * 线程允许空闲时间
     */
    private static long KEEP_ALIVE_TIME = 10;

    /**
     * 线程池使用缓冲队列大小
     */
    private static int WORK_QUEUE_SIZE = 1000;

    public static void setCorePoolSize(int corePoolSize) {
        CORE_POOL_SIZE = corePoolSize;
    }

    public static void setMaxPoolSize(int maxPoolSize) {
        MAX_POOL_SIZE = maxPoolSize;
    }

    public static void setKeepAliveTime(long keepAliveTime) {
        KEEP_ALIVE_TIME = keepAliveTime;
    }

    public static void setWorkQueueSize(int workQueueSize) {
        WORK_QUEUE_SIZE = workQueueSize;
    }

    private ThreadPoolExecutor threadPool;

    public SchedulerThreadHelper() {
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(WORK_QUEUE_SIZE),
                new ThreadPoolExecutor.CallerRunsPolicy());
        return;
    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void post(Runnable runnable) {
        threadPool.execute(runnable);
    }
}
