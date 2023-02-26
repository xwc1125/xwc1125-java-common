/**
 *  @Project ZzxSDK-3.0 
 *  @Package com.zzx.framework.util.uuid
 *
 * @Title IdWorker.java
 * @Description
 * @Copyright Copyright (c) 2016
 * @author xwc1125
 * @date 2016年2月29日 上午10:48:43 
 * @version V1.0
 */
package com.xwc1125.common.util.uuid;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Random;

/**
 * from
 * https://github.com/twitter/snowflake/blob/master/src/main/scala/com/twitter
 * /service/snowflake/IdWorker.scala
 *
 * @author adyliu (imxylz@gmail.com)
 * @since 1.0
 */
public class IdWorker {
    /**
     * workerId是实际server机器的代号，最大到32，同一个datacenter下的workerId是不能重复的。
     */
    private final long workerId;
    /**
     * 1.方便搭建多个生成uid的service，并保证uid不重复，比如在datacenter0将机器0，1，2组成了一个生成uid的service，
     * 而datacenter1此时也需要一个生成uid的service
     * ，从本中心获取uid显然是最快最方便的，那么它可以在自己中心搭建，只要保证datacenterId唯一
     * 。如果没有datacenterId，即用10bits
     * ，那么在搭建一个新的service前必须知道目前已经在用的id，否则不能保证生成的id唯一，比如搭建的两个uid
     * service中都有machine id为100的机器，如果其server时间相同，那么产生相同id的情况不可避免。
     *
     * 2.加快server启动速度。启动一台uid
     * server时，会去检查zk同workerId目录中其他机器的情况，如其在zk上注册的id和向它请求返回的work_id是否相同
     * ，是否处同一个datacenter下
     * ，另外还会检查该server的时间与目前已有机器的平均时间误差是否在10s范围内等，这些检查是会耗费一定时间的。
     * 将一个datacenter下的机器数限制在32台(5bits)以内，在一定程度上也保证了server的启动速度。
     */
    private final long datacenterId;
    /**
     * id的起始时间
     */
    private final long idepoch;

    private static final long workerIdBits = 5L;
    private static final long datacenterIdBits = 5L;
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private static final long sequenceBits = 12L;
    private static final long workerIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits
            + datacenterIdBits;
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;
    private long sequence;
    private static final Random r = new Random();

    public IdWorker() {
        this(1344322705519L);
    }

    public IdWorker(long idepoch) {
        this(r.nextInt((int) maxWorkerId), r.nextInt((int) maxDatacenterId), 0,
                idepoch);
    }

    public IdWorker(long workerId, long datacenterId) {
        this(workerId, datacenterId, 0L, 1344322705519L);
    }

    public IdWorker(long workerId, long datacenterId, long sequence) {
        this(workerId, datacenterId, sequence, 1344322705519L);
    }

    /**
     *
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param workerId
     *            服务器ID，最大值1023，可以支持开到1000多个服务器
     * @param datacenterId
     *            自增序列最大值为 1023
     * @param sequence
     * @param idepoch
     */
    public IdWorker(long workerId, long datacenterId, long sequence,
                    long idepoch) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
        this.idepoch = idepoch;
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException("workerId is illegal: "
                    + workerId);
        }
        if (datacenterId < 0 || datacenterId > maxDatacenterId) {
            throw new IllegalArgumentException("datacenterId is illegal: "
                    + workerId);
        }
        if (idepoch >= System.currentTimeMillis()) {
            throw new IllegalArgumentException("idepoch is illegal: " + idepoch);
        }
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public long getId() {
        long id = nextId();
        return id;
    }

    private synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        long id = ((timestamp - idepoch) << timestampLeftShift)//
                | (datacenterId << datacenterIdShift)//
                | (workerId << workerIdShift)//
                | sequence;
        return id;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IdWorker{");
        sb.append("workerId=").append(workerId);
        sb.append(", datacenterId=").append(datacenterId);
        sb.append(", idepoch=").append(idepoch);
        sb.append(", lastTimestamp=").append(lastTimestamp);
        sb.append(", sequence=").append(sequence);
        sb.append('}');
        return sb.toString();
    }
}
