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

import com.hivemq.plugins.plugin.SysTopicConfig;
import com.hivemq.plugins.plugin.SysTopicRegistration;
import com.hivemq.spi.callback.CallbackPriority;
import com.hivemq.spi.callback.events.broker.OnBrokerStart;
import com.hivemq.spi.callback.exception.BrokerUnableToStartException;
import com.hivemq.spi.services.PluginExecutorService;
import com.hivemq.spi.services.SYSTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

/**
 * @author Lukas Brandl
 */
public class BrokerStart implements OnBrokerStart {

    private final Logger log = LoggerFactory.getLogger(BrokerStart.class);


    private final SYSTopicService sysTopicService;
    private final SysTopicConfig sysTopicConfig;
    private final PluginExecutorService pluginExecutorService;
    private final SysTopicRegistration sysTopicRegistration;

    @Inject
    public BrokerStart(final SYSTopicService sysTopicService,
                       final SysTopicConfig sysTopicConfig,
                       final PluginExecutorService pluginExecutorService,
                       final SysTopicRegistration sysTopicRegistration) {
        this.sysTopicService = sysTopicService;
        this.sysTopicConfig = sysTopicConfig;
        this.pluginExecutorService = pluginExecutorService;
        this.sysTopicRegistration = sysTopicRegistration;
    }

    @Override
    public void onBrokerStart() throws BrokerUnableToStartException {
        sysTopicRegistration.registerSysTopics();

        final int publishInterval = sysTopicConfig.getPublishInterval();
        pluginExecutorService.scheduleAtFixedRate(new TriggerDynamicSysTopic(), publishInterval, publishInterval, TimeUnit.SECONDS);

    }

    private class TriggerDynamicSysTopic implements Runnable {

        @Override
        public void run() {
            log.trace("Triggered standard sys topic publish.");
            sysTopicService.triggerStandardSysTopicPublish();
        }
    }

    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }


}
