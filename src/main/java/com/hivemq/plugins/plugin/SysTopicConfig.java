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

import com.google.common.annotations.VisibleForTesting;
import com.hivemq.spi.config.SystemInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Lukas Brandl
 */
public class SysTopicConfig {

    private static final Logger log = LoggerFactory.getLogger(SysTopicConfig.class);
    private static final String PUBLISH_INTERVAL_PROPERTY = "publishInterval";

    private final SystemInformation systemInformation;

    @VisibleForTesting
    final long defaultPublishInterval = 60;

    private long publishInterval;

    @Inject
    SysTopicConfig(SystemInformation systemInformation) {
        this.systemInformation = systemInformation;
    }

    @VisibleForTesting
    @PostConstruct
    void readProperties() {

        publishInterval = defaultPublishInterval; // Use default, in case no valid publish interval provided.

        final File configFolder = systemInformation.getConfigFolder();
        final File propertiesFile = new File(configFolder, "systopic-plugin.properties");

        if (!propertiesFile.canRead()) {
            log.warn("Could not read {}. Default properties are used.", propertiesFile.getAbsolutePath());
            return;
        }

        try (InputStream is = new FileInputStream(propertiesFile)) {

            log.debug("Reading property file {}", propertiesFile.getAbsolutePath());

            final Properties properties = new Properties();
            properties.load(is);
            final String publishIntervalString = properties.getProperty(PUBLISH_INTERVAL_PROPERTY);
            final int publishIntervalFromProperties = Integer.parseInt(publishIntervalString);

            if (validatePublishInterval(publishIntervalFromProperties)) {
                publishInterval = publishIntervalFromProperties;
            }

        } catch (IOException e) {
            log.error("An error occurred while reading the properties file {}. Default properties are used.", propertiesFile.getAbsolutePath(), e);
        }
    }

    private boolean validatePublishInterval(final int publishInterval) {
        if (publishInterval <= 0) {
            return false;
        }
        return true;
    }

    public long getPublishInterval() {
        return publishInterval;
    }
}