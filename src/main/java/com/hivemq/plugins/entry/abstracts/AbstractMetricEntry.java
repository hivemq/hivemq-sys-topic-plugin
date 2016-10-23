package com.hivemq.plugins.entry.abstracts;

import com.hivemq.spi.services.BlockingMetricService;

/**
 * Created by gheld on 07.09.16.
 */
public abstract class AbstractMetricEntry {
    protected final BlockingMetricService metricService;

    protected AbstractMetricEntry(final BlockingMetricService metricService) {
        this.metricService = metricService;
    }
}
