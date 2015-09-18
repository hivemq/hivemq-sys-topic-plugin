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

package com.hivemq.plugins.entry;

import com.codahale.metrics.Meter;
import com.google.common.base.Supplier;
import com.hivemq.spi.topic.sys.SYSTopicEntry;
import com.hivemq.spi.topic.sys.Type;

/**
 * @author Lukas Brandl
 */
public abstract class RateEntry implements SYSTopicEntry {

    protected abstract Meter meter();

    private final RateEntry rateEntry;

    protected RateEntry() {
        rateEntry = this;
    }

    public SYSTopicEntry oneMinute() {
        return new MinutesSysTopicEntry(1, rateEntry) {
            @Override
            protected byte[] supplierValue() {
                return Double.toString(meter().getOneMinuteRate()).getBytes();
            }
        };
    }

    public SYSTopicEntry fiveMinutes() {
        return new MinutesSysTopicEntry(5, rateEntry) {
            @Override
            protected byte[] supplierValue() {
                return Double.toString(meter().getFiveMinuteRate()).getBytes();
            }
        };
    }

    public SYSTopicEntry fifteenMinutes() {
        return new MinutesSysTopicEntry(15, rateEntry) {
            @Override
            protected byte[] supplierValue() {
                return Double.toString(meter().getFifteenMinuteRate()).getBytes();
            }
        };
    }

    @Override
    public final Supplier<byte[]> payload() {
        throw new UnsupportedOperationException();
    }

    private abstract class MinutesSysTopicEntry implements SYSTopicEntry {
        private final int minute;

        protected abstract byte[] supplierValue();

        private RateEntry rateEntry;

        public MinutesSysTopicEntry(final int minute, final RateEntry rateEntry) {
            this.minute = minute;
            this.rateEntry = rateEntry;
        }

        @Override
        public String topic() {
            return rateEntry.topic() + minute + "min";
        }

        @Override
        public Supplier<byte[]> payload() {
            return new Supplier<byte[]>() {
                @Override
                public byte[] get() {
                    return supplierValue();
                }
            };
        }

        @Override
        public Type type() {
            return rateEntry.type();
        }

        @Override
        public String description() {
            return rateEntry.description();
        }
    }

}
