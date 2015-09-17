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

import com.google.inject.Inject;
import com.hivemq.plugins.entry.*;
import com.hivemq.spi.services.SYSTopicService;
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

    @Inject
    public SysTopicRegistration(final SYSTopicService sysTopicService,
                                final BytesReceivedTotal bytesReceivedTotal,
                                final BytesSentTotal bytesSentTotal,
                                final ClientsConnectedCurrent clientsConnectedCurrent,
                                final ClientsDisconnectedTotal clientsDisconnectedTotal,
                                final ClientsConnectedMaximum clientsConnectedMaximum,
                                final ClientsTotal clientsTotal) {
        this.sysTopicService = sysTopicService;

        this.bytesReceivedTotal = bytesReceivedTotal;
        this.bytesSentTotal = bytesSentTotal;
        this.clientsConnectedCurrent = clientsConnectedCurrent;
        this.clientsDisconnectedTotal = clientsDisconnectedTotal;
        this.clientsConnectedMaximum = clientsConnectedMaximum;
        this.clientsTotal = clientsTotal;
    }

    public void registerSysTopics() {
        log.debug("Added SYS topic entry for {}", bytesReceivedTotal.topic());
        sysTopicService.addEntry(bytesReceivedTotal);

        log.debug("Added SYS topic entry for {}", bytesSentTotal.topic());
        sysTopicService.addEntry(bytesSentTotal);

        log.debug("Added SYS topic entry for {}", clientsConnectedCurrent.topic());
        sysTopicService.addEntry(clientsConnectedCurrent);

        log.debug("Added SYS topic entry for {}", clientsDisconnectedTotal.topic());
        sysTopicService.addEntry(clientsDisconnectedTotal);

        log.debug("Added SYS topic entry for {}", clientsConnectedMaximum.topic());
        sysTopicService.addEntry(clientsConnectedMaximum);

        log.debug("Added SYS topic entry for {}", clientsTotal.topic());
        sysTopicService.addEntry(clientsTotal);


    }

}
