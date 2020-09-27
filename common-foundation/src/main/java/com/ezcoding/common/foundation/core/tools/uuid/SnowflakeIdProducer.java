package com.ezcoding.common.foundation.core.tools.uuid;

/**
 * 使用Twitter的分布式自增ID雪花算法snowflake
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-27 9:33
 */
public class SnowflakeIdProducer implements IdProduceable {

    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;

    /**
     * 序列号占用的位数
     */
    public final static long SEQUENCE_BIT = 10;

    /**
     * 机器标识占用的位数
     */
    public final static long MACHINE_BIT = 10;

    /**
     * 数据中心占用的位数
     */
    public final static long DATACENTER_BIT = 2;

    /**
     * 每一部分的最大值
     */
    public final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    public final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    public final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 数据中心
     */
    private final long datacenterId;

    /**
     * 机器标识
     */
    private final long machineId;

    /**
     * 序列号
     */
    private long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private long lastStmp = -1L;

    public SnowflakeIdProducer(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId must greater or equal to 0" + MAX_DATACENTER_NUM);
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId must greater or equal to 0" + MAX_MACHINE_NUM);
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    @Override
    public synchronized String produce() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("machine clock back is detected and the corresponding serial number cannot be generated");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return String.valueOf(
                (currStmp - START_STMP) << TIMESTMP_LEFT
                        | datacenterId << DATACENTER_LEFT
                        | machineId << MACHINE_LEFT
                        | sequence
        );
    }

}
