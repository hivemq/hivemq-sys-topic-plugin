package com.hivemq.plugins.plugin;

import com.google.inject.Inject;
import com.hivemq.plugins.entry.BytesReceived;
import com.hivemq.spi.services.SYSTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukas Brandl
 */
public class SysTopicRegistration {

    private final Logger log = LoggerFactory.getLogger(SysTopicRegistration.class);

    private final SYSTopicService sysTopicService;

    private final BytesReceived bytesReceived;

    @Inject
    public SysTopicRegistration(final SYSTopicService sysTopicService,
                                final BytesReceived bytesReceived) {
        this.sysTopicService = sysTopicService;

        this.bytesReceived = bytesReceived;
    }

    public void registerSysTopics() {
        log.debug("Added SYS topic entry for {}", bytesReceived.topic());
        sysTopicService.addEntry(bytesReceived);

    }

}
