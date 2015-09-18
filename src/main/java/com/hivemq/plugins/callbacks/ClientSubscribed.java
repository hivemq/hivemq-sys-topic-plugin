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

import com.google.inject.Inject;
import com.hivemq.spi.callback.lowlevel.OnSubackSend;
import com.hivemq.spi.message.SUBACK;
import com.hivemq.spi.security.ClientData;
import com.hivemq.spi.services.SYSTopicService;

/**
 * @author Lukas Brandl
 */
public class ClientSubscribed implements OnSubackSend {

    private final SYSTopicService sysTopicService;

    @Inject
    public ClientSubscribed(final SYSTopicService sysTopicService) {
        this.sysTopicService = sysTopicService;
    }

    @Override
    public void onSubackSend(SUBACK suback, ClientData clientData) {
        final String clientId = clientData.getClientId();
        sysTopicService.triggerStandardSysTopicPublishToClient(clientId);
        sysTopicService.triggerStaticSysTopicPublishToClient(clientId);
    }
}
