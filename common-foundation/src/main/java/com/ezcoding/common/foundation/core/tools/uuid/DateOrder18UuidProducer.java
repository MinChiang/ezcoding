package com.ezcoding.common.foundation.core.tools.uuid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 根据时间排序生成18位的uuid
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-27 9:32
 */
@Deprecated
public class DateOrder18UuidProducer implements IdProduceable {

    private static final DateTimeFormatter SECOND_FORMATTER = DateTimeFormatter.ofPattern("YYYYMMddHHmmss");
    private static final Integer MAX = 0X1FFF;
    private static final Integer MAX_LENGTH = String.valueOf(MAX).length();
    private static final AtomicLong ATOMIC_LONG = new AtomicLong(0L);

    @Override
    public String produce() {
        String format = LocalDateTime.now().format(SECOND_FORMATTER);
        String s = String.valueOf(ATOMIC_LONG.getAndIncrement() & MAX);
        StringBuilder sb = new StringBuilder(format);
        int length = MAX_LENGTH - s.length();
        for (int i = 0; i < length; i++) {
            sb.append("0");
        }
        sb.append(s);
        return sb.toString();
    }

}
