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

package com.hivemq.plugins.plugin;

import com.hivemq.plugins.entry.*;
import com.hivemq.spi.services.SYSTopicService;
import com.hivemq.spi.topic.sys.SYSTopicEntry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SysTopicRegistrationTest {

    @Mock
    BytesReceivedTotal bytesReceivedTotal;
    @Mock
    BytesSentTotal bytesSentTotal;
    @Mock
    ClientsConnectedCurrent clientsConnectedCurrent;
    @Mock
    ClientsDisconnectedTotal clientsDisconnectedTotal;
    @Mock
    ClientsConnectedMaximum clientsConnectedMaximum;
    @Mock
    ClientsTotal clientsTotal;
    @Mock
    MessagesDroppedTotal messagesDroppedTotal;
    @Mock
    MessagesReceivedTotal messagesReceivedTotal;
    @Mock
    MessagesSentTotal messagesSentTotal;
    @Mock
    PublishReceivedTotal publishReceivedTotal;
    @Mock
    PublishSentTotal publishSentTotal;
    @Mock
    RetainedCurrent retainedCurrent;
    @Mock
    SubscriptionsCurrent subscriptionsCurrent;
    @Mock
    TimeCurrent timeCurrent;
    @Mock
    UpTimeTotal upTimeTotal;
    @Mock
    VersionCurrent versionCurrent;
    @Mock
    ConnectionsRate connectionsRate;
    @Mock
    MessagesDroppedRate messagesDroppedRate;
    @Mock
    MessagesReceivedRate messagesReceivedRate;
    @Mock
    MessagesSentRate messagesSentRate;
    @Mock
    PublishSentRate publishSentRate;
    @Mock
    PublishReceivedRate publishReceivedRate;

    @Mock
    SYSTopicEntry oneMin;
    @Mock
    SYSTopicEntry fiveMin;
    @Mock
    SYSTopicEntry fifteenMin;

    @Mock
    SYSTopicService sysTopicService;

    SysTopicRegistration sysTopicRegistration;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        sysTopicRegistration = new SysTopicRegistration(sysTopicService,
                bytesReceivedTotal,
                bytesSentTotal,
                clientsConnectedCurrent,
                clientsDisconnectedTotal,
                clientsConnectedMaximum,
                clientsTotal,
                messagesDroppedTotal,
                messagesReceivedTotal,
                messagesSentTotal,
                publishReceivedTotal,
                publishSentTotal,
                retainedCurrent,
                subscriptionsCurrent,
                timeCurrent,
                upTimeTotal,
                versionCurrent,
                connectionsRate,
                messagesDroppedRate,
                messagesReceivedRate,
                messagesSentRate,
                publishSentRate,
                publishReceivedRate);

        when(messagesDroppedRate.oneMinute()).thenReturn(oneMin);
        when(messagesReceivedRate.oneMinute()).thenReturn(oneMin);
        when(messagesSentRate.oneMinute()).thenReturn(oneMin);
        when(publishSentRate.oneMinute()).thenReturn(oneMin);
        when(publishReceivedRate.oneMinute()).thenReturn(oneMin);
        when(connectionsRate.oneMinute()).thenReturn(oneMin);

        when(messagesDroppedRate.fiveMinutes()).thenReturn(fiveMin);
        when(messagesReceivedRate.fiveMinutes()).thenReturn(fiveMin);
        when(messagesSentRate.fiveMinutes()).thenReturn(fiveMin);
        when(publishSentRate.fiveMinutes()).thenReturn(fiveMin);
        when(publishReceivedRate.fiveMinutes()).thenReturn(fiveMin);
        when(connectionsRate.fiveMinutes()).thenReturn(fiveMin);

        when(messagesDroppedRate.fifteenMinutes()).thenReturn(fifteenMin);
        when(messagesReceivedRate.fifteenMinutes()).thenReturn(fifteenMin);
        when(messagesSentRate.fifteenMinutes()).thenReturn(fifteenMin);
        when(publishSentRate.fifteenMinutes()).thenReturn(fifteenMin);
        when(publishReceivedRate.fifteenMinutes()).thenReturn(fifteenMin);
        when(connectionsRate.fifteenMinutes()).thenReturn(fifteenMin);

        when(bytesReceivedTotal.topic()).thenReturn("topic");
        when(bytesSentTotal.topic()).thenReturn("topic");
        when(clientsConnectedCurrent.topic()).thenReturn("topic");
        when(clientsDisconnectedTotal.topic()).thenReturn("topic");
        when(clientsConnectedMaximum.topic()).thenReturn("topic");
        when(clientsTotal.topic()).thenReturn("topic");
        when(messagesDroppedTotal.topic()).thenReturn("topic");
        when(messagesReceivedTotal.topic()).thenReturn("topic");
        when(messagesSentTotal.topic()).thenReturn("topic");
        when(publishReceivedTotal.topic()).thenReturn("topic");
        when(publishSentTotal.topic()).thenReturn("topic");
        when(retainedCurrent.topic()).thenReturn("topic");
        when(subscriptionsCurrent.topic()).thenReturn("topic");
        when(timeCurrent.topic()).thenReturn("topic");
        when(upTimeTotal.topic()).thenReturn("topic");
        when(versionCurrent.topic()).thenReturn("topic");
        when(connectionsRate.topic()).thenReturn("topic");
        when(messagesDroppedRate.topic()).thenReturn("topic");
        when(messagesReceivedRate.topic()).thenReturn("topic");
        when(messagesSentRate.topic()).thenReturn("topic");
        when(publishSentRate.topic()).thenReturn("topic");
        when(publishReceivedRate.topic()).thenReturn("topic");

        when(oneMin.topic()).thenReturn("topic");
        when(fiveMin.topic()).thenReturn("topic");
        when(fifteenMin.topic()).thenReturn("topic");

    }

    @Test
    public void test_register_sys_topic_entries() throws Exception {
        sysTopicRegistration.registerSysTopics();

        verify(sysTopicService, times(1)).addEntry(bytesReceivedTotal);
        verify(sysTopicService, times(1)).addEntry(bytesSentTotal);
        verify(sysTopicService, times(1)).addEntry(clientsConnectedCurrent);
        verify(sysTopicService, times(1)).addEntry(clientsDisconnectedTotal);
        verify(sysTopicService, times(1)).addEntry(clientsConnectedMaximum);
        verify(sysTopicService, times(1)).addEntry(clientsTotal);
        verify(sysTopicService, times(1)).addEntry(messagesDroppedTotal);
        verify(sysTopicService, times(1)).addEntry(messagesReceivedTotal);
        verify(sysTopicService, times(1)).addEntry(messagesSentTotal);
        verify(sysTopicService, times(1)).addEntry(publishReceivedTotal);
        verify(sysTopicService, times(1)).addEntry(publishSentTotal);
        verify(sysTopicService, times(1)).addEntry(retainedCurrent);
        verify(sysTopicService, times(1)).addEntry(subscriptionsCurrent);
        verify(sysTopicService, times(1)).addEntry(timeCurrent);
        verify(sysTopicService, times(1)).addEntry(upTimeTotal);
        verify(sysTopicService, times(1)).addEntry(versionCurrent);

        verify(sysTopicService, times(6)).addEntry(oneMin);
        verify(sysTopicService, times(6)).addEntry(fiveMin);
        verify(sysTopicService, times(6)).addEntry(fifteenMin);
    }

}