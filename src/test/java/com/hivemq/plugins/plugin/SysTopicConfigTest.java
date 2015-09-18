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

import com.google.common.io.Files;
import com.hivemq.spi.config.SystemInformation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SysTopicConfigTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    SystemInformation systemInformation;


    private SysTopicConfig reader;
    private File configFolder;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        configFolder = temporaryFolder.newFolder();
        when(systemInformation.getConfigFolder()).thenReturn(configFolder);
        reader = new SysTopicConfig(systemInformation);
    }

    @Test
    public void test_read_properties() throws Exception {

        Files.write("publishInterval = 10", new File(configFolder, "systopic-plugin.properties"), StandardCharsets.ISO_8859_1);

        reader.readProperties();
        final long publishInterval = reader.getPublishInterval();

        assertEquals(10, publishInterval);

    }

    @Test
    public void test_read_invalid_properties() throws Exception {

        Files.write("publishInterval = -10", new File(configFolder, "cloudwatch_metrics.properties"), StandardCharsets.ISO_8859_1);

        reader.readProperties();
        final long publishInterval = reader.getPublishInterval();

        assertEquals(reader.defaultPublishInterval, publishInterval);
    }

    @Test
    public void test_no_file() throws Exception {
        reader.readProperties();
        final long publishInterval = reader.getPublishInterval();

        assertEquals(reader.defaultPublishInterval, publishInterval);
    }

}