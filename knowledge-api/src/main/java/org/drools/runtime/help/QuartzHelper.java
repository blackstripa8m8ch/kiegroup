/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.core.runtime.help;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.drools.core.time.Calendar;

public class QuartzHelper {

    public static Calendar quartzCalendarAdapter(org.quartz.Calendar calendar) {
        return new QuartzCalendarAdapter( calendar );
    }

    public static class QuartzCalendarAdapter
        implements
        Calendar,
        Externalizable {
        private org.quartz.Calendar calendar;

        public QuartzCalendarAdapter(org.quartz.Calendar calendar) {
            this.calendar = calendar;
        }

        public boolean isTimeIncluded(long timestamp) {
            return this.calendar.isTimeIncluded( timestamp );
        }

        public void readExternal(ObjectInput in) throws IOException,
                                                ClassNotFoundException {
            this.calendar = (org.quartz.Calendar) in.readObject();
        }

        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject( this.calendar );
        }

    }

}
