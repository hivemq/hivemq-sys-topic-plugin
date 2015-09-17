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

import com.google.inject.Inject;
import com.hivemq.plugins.entry.*;
import com.hivemq.spi.services.SYSTopicService;
import com.hivemq.spi.topic.sys.SYSTopicEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukas Brandl
 */
public class SysTopicRegistration {

    private final Logger log = LoggerFactory.getLogger(SysTopicRegistration.class);

    private final SYSTopicService sysTopicService;

    private final BytesReceivedTotal bytesReceivedTotal;
    private final BytesSentTotal bytesSentTotal;
    private final ClientsConnectedCurrent clientsConnectedCurrent;
    private final ClientsDisconnectedTotal clientsDisconnectedTotal;
    private final ClientsConnectedMaximum clientsConnectedMaximum;
    private final ClientsTotal clientsTotal;
    private final MessagesDroppedTotal messagesDroppedTotal;
    private final MessagesReceivedTotal messagesReceivedTotal;
    private final MessagesSentTotal messagesSentTotal;
    private final PublishReceivedTotal publishReceivedTotal;
    private final PublishSentTotal publishSentTotal;
    private final RetainedCurrent retainedCurrent;
    private final SubscriptionsCurrent subscriptionsCurrent;
    private final TimeCurrent timeCurrent;
    private final UpTimeTotal upTimeTotal;
    private final VersionCurrent versionCurrent;
    private final ConnectionsRate connectionsRate;

    @Inject
    public SysTopicRegistration(final SYSTopicService sysTopicService,
                                final BytesReceivedTotal bytesReceivedTotal,
                                final BytesSentTotal bytesSentTotal,
                                final ClientsConnectedCurrent clientsConnectedCurrent,
                                final ClientsDisconnectedTotal clientsDisconnectedTotal,
                                final ClientsConnectedMaximum clientsConnectedMaximum,
                                final ClientsTotal clientsTotal,
                                final MessagesDroppedTotal messagesDroppedTotal,
                                final MessagesReceivedTotal messagesReceivedTotal,
                                final MessagesSentTotal messagesSentTotal,
                                final PublishReceivedTotal publishReceivedTotal,
                                final PublishSentTotal publishSentTotal,
                                final RetainedCurrent retainedCurrent,
                                final SubscriptionsCurrent subscriptionsCurrent,
                                final TimeCurrent timeCurrent,
                                final UpTimeTotal upTimeTotal,
                                final VersionCurrent versionCurrent,
                                final ConnectionsRate connectionsRate) {

        this.sysTopicService = sysTopicService;

        this.bytesReceivedTotal = bytesReceivedTotal;
        this.bytesSentTotal = bytesSentTotal;
        this.clientsConnectedCurrent = clientsConnectedCurrent;
        this.clientsDisconnectedTotal = clientsDisconnectedTotal;
        this.clientsConnectedMaximum = clientsConnectedMaximum;
        this.clientsTotal = clientsTotal;
        this.messagesDroppedTotal = messagesDroppedTotal;
        this.messagesReceivedTotal = messagesReceivedTotal;
        this.messagesSentTotal = messagesSentTotal;
        this.publishReceivedTotal = publishReceivedTotal;
        this.publishSentTotal = publishSentTotal;
        this.retainedCurrent = retainedCurrent;
        this.subscriptionsCurrent = subscriptionsCurrent;
        this.timeCurrent = timeCurrent;
        this.upTimeTotal = upTimeTotal;
        this.versionCurrent = versionCurrent;
        this.connectionsRate = connectionsRate;
    }

    public void registerSysTopics() {

        registerEntry(bytesReceivedTotal);
        registerEntry(bytesSentTotal);
        registerEntry(clientsConnectedCurrent);
        registerEntry(clientsDisconnectedTotal);
        registerEntry(clientsConnectedMaximum);
        registerEntry(clientsTotal);
        registerEntry(messagesDroppedTotal);
        registerEntry(messagesReceivedTotal);
        registerEntry(messagesSentTotal);
        registerEntry(publishReceivedTotal);
        registerEntry(publishSentTotal);
        registerEntry(retainedCurrent);
        registerEntry(subscriptionsCurrent);
        registerEntry(timeCurrent);
        registerEntry(upTimeTotal);
        registerEntry(versionCurrent);

        registerRateEntry(connectionsRate);


    }

    private void registerEntry(final SYSTopicEntry sysTopicEntry) {
        log.debug("Added SYS topic entry for {}", sysTopicEntry.topic());
        sysTopicService.addEntry(sysTopicEntry);
    }

    private void registerRateEntry(final RateEntry rateEntry) {

        SYSTopicEntry rate1Min = rateEntry.oneMinute();
        registerEntry(rate1Min);

        SYSTopicEntry rate5Min = rateEntry.fiveMinutes();
        registerEntry(rate5Min);

        SYSTopicEntry rate15Min = rateEntry.fifteenMinutes();
        registerEntry(rate15Min);
    }

}
