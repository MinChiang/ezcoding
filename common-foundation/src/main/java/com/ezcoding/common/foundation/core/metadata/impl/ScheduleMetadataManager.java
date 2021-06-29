package com.ezcoding.common.foundation.core.metadata.impl;

import com.ezcoding.common.foundation.core.metadata.BaseMetadataFetchable;
import com.ezcoding.common.foundation.core.metadata.MetadataManageable;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-29 10:15
 */
public class ScheduleMetadataManager extends AggregateMetadataManager {

    private static final long TEN_MINUTES = 10 * 60 * 1_000;
    private final Timer timer;

    public ScheduleMetadataManager(Timer timer, long period) {
        this(null, timer, period);
    }

    public ScheduleMetadataManager(List<BaseMetadataFetchable> fetchers, Timer timer, long period) {
        super(fetchers);
        if (timer == null) {
            throw new IllegalArgumentException("timer can not be null");
        }
        if (period <= 1_000) {
            throw new IllegalArgumentException("reload period can not less than 1 seconds");
        }
        this.timer = timer;
        this.timer.scheduleAtFixedRate(new LoadJob(this), 0, period);
    }

    public ScheduleMetadataManager(long period) {
        this(new Timer(true), period);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                timer.cancel();
            }
        });
    }

    public ScheduleMetadataManager() {
        this(TEN_MINUTES);
    }

    private static class LoadJob extends TimerTask {

        private final MetadataManageable fetcher;

        public LoadJob(MetadataManageable fetcher) {
            this.fetcher = fetcher;
        }

        @Override
        public void run() {
            this.fetcher.load();
        }

    }

}
