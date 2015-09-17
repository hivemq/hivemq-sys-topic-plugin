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

package com.hivemq.plugins.plugin;

import com.hivemq.plugins.callbacks.BrokerStart;
import com.hivemq.plugins.callbacks.ClientSubscribed;
import com.hivemq.spi.PluginEntryPoint;
import com.hivemq.spi.callback.registry.CallbackRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Lukas Brandl
 */
public class SysTopicMetricsMainClass extends PluginEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(SysTopicMetricsMainClass.class);

    private final BrokerStart brokerStart;
    private final ClientSubscribed clientSubscribed;

    @Inject
    public SysTopicMetricsMainClass(final BrokerStart brokerStart,
                                    final ClientSubscribed clientSubscribed) {
        this.brokerStart = brokerStart;
        this.clientSubscribed = clientSubscribed;
    }

    @PostConstruct
    public void postConstruct() {
        CallbackRegistry callbackRegistry = getCallbackRegistry();

        log.debug("Registered Broker Start Callback for the Sys Topic Plugin .");
        callbackRegistry.addCallback(brokerStart);
        callbackRegistry.addCallback(clientSubscribed);
    }

}
