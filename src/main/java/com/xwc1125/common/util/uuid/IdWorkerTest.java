/**
 *  @Project ZzxSDK-3.0 
 *  @Package com.zzx.framework.util.uuid
 *
 * @Title IdWorkerTest.java
 * @Description
 * @Copyright Copyright (c) 2016
 * @author xwc1125
 * @date 2016年2月29日 上午10:19:33 
 * @version V1.0
 */
package com.xwc1125.common.util.uuid;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName IdWorkerTest
 * @Description TODO(describe the types)
 * @author xwc1125
 * @date 2016年2月29日 上午10:19:33
 */
public class IdWorkerTest {

    static class IdWorkThread implements Runnable {
        private Set<Long> set;
        private IdWorker idWorker;

        public IdWorkThread(Set<Long> set, IdWorker idWorker) {
            this.set = set;
            this.idWorker = idWorker;
        }

        @Override
        public void run() {
            while (true) {
                long id = idWorker.getId();
                System.out.println(id);
                if (!set.add(id)) {
                    System.out.println("duplicate:" + id);
                }
            }
        }
    }

    public static void main(String[] args) {
        Set<Long> set = new HashSet<Long>();
        final IdWorker idWorker1 = new IdWorker(0, 0);
        final IdWorker IdWorker = new IdWorker(1, 0);
        System.out.println(idWorker1.getId());
        System.out.println(idWorker1.getId());
        System.out.println(IdWorker.getId());

        IdWorker idWorker = new IdWorker(1, 1, 1);
        System.out.println(idWorker.getId());

        Thread t1 = new Thread(new IdWorkThread(set, idWorker1));
        Thread t2 = new Thread(new IdWorkThread(set, IdWorker));
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
