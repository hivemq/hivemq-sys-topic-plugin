/*
 * Copyright 2015 dc-square GmbH
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
import com.hivemq.spi.services.PluginExecutorService;
import com.hivemq.spi.services.SYSTopicService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class BrokerStartTest {

    @Mock
    SYSTopicService sysTopicService;

    @Mock
    SysTopicConfig sysTopicConfig;

    @Mock
    SysTopicRegistration sysTopicRegistration;

    @Mock
    PluginExecutorService pluginExecutorService;

    BrokerStart brokerStart;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        brokerStart = new BrokerStart(sysTopicService, sysTopicConfig, pluginExecutorService, sysTopicRegistration);
    }

    @Test
    public void test_broker_start() throws Exception {
        final long publishInterval = 60;
        when(sysTopicConfig.getPublishInterval()).thenReturn(publishInterval);

        brokerStart.onBrokerStart();

        verify(sysTopicRegistration, times(1)).registerSysTopics();
        verify(pluginExecutorService, times(1)).scheduleAtFixedRate(any(Runnable.class), eq(publishInterval), eq(publishInterval), eq(TimeUnit.SECONDS));
    }
}