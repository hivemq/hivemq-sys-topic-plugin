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

import com.hivemq.spi.message.SUBACK;
import com.hivemq.spi.security.ClientData;
import com.hivemq.spi.services.SYSTopicService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ClientSubscribedTest {

    @Mock
    SYSTopicService sysTopicService;

    @Mock
    ClientData clientData;

    @Mock
    SUBACK suback;

    ClientSubscribed clientSubscribed;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        clientSubscribed = new ClientSubscribed(sysTopicService);
    }

    @Test
    public void test_client_subscribed() throws Exception {
        final String clientId = "clientId";
        when(clientData.getClientId()).thenReturn(clientId);

        clientSubscribed.onSubackSend(suback, clientData);

        verify(sysTopicService, times(1)).triggerStaticSysTopicPublishToClient(clientId);
        verify(sysTopicService, times(1)).triggerStandardSysTopicPublishToClient(clientId);
    }
}