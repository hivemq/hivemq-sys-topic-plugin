/*
 * Copyright 2013 dc-square GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hivemq.plugins.callbacks;

import com.codahale.metrics.MetricRegistry;
import com.hivemq.spi.callback.events.OnSubscribeCallback;
import com.hivemq.spi.callback.events.broker.OnBrokerStart;
import com.hivemq.spi.callback.exception.BrokerUnableToStartException;
import com.hivemq.spi.callback.exception.InvalidSubscriptionException;
import com.hivemq.spi.callback.schedule.ScheduledCallback;
import com.hivemq.spi.message.SUBSCRIBE;
import com.hivemq.spi.security.ClientData;
import com.hivemq.spi.services.SYSTopicService;
import com.hivemq.spi.topic.sys.SYSTopicEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Florian Limpoeck
 */
public class SysTopicReporting implements OnBrokerStart, ScheduledCallback, OnSubscribeCallback {

    Logger log = LoggerFactory.getLogger(SysTopicReporting.class);

    private final MetricRegistry metricRegistry;
    private SYSTopicEntry sysTopicEntry;
    private SYSTopicService sysTopicService;

    @Inject
    public SysTopicReporting(final MetricRegistry metricRegistry){
        this.metricRegistry = metricRegistry;
    }

    @Override
    public void onBrokerStart() throws BrokerUnableToStartException {

        System.out.println("HEYHO");
        final Collection<SYSTopicEntry> allEntries = sysTopicService.getAllEntries();
        for (SYSTopicEntry allEntry : allEntries) {
            final String topic = allEntry.topic();
            System.out.println(topic);
        }
    }

    @Override
    public void execute() {
        sysTopicService.triggerStandardSysTopicPublish();
        log.info("systopic scheduled service started from my mama");
    }

    @Override
    public String cronExpression() {
        return "0 4 30 ? * TUE";
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void onSubscribe(SUBSCRIBE message, ClientData clientData) throws InvalidSubscriptionException {



    }
}
