/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
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

package org.kie.api.event.process;

import java.util.Collections;
import java.util.Map;

import org.kie.api.runtime.process.NodeInstance;

/**
 * An event related to the execution of a node instance within a process instance.
 */
public interface ProcessNodeEvent
    extends
    ProcessEvent {

    /**
     * The node instance this event is related to.
     *
     * @return the node instance
     */
    NodeInstance getNodeInstance();

    /**
     * This allow to add some extra data across node events
     * @return
     */
    default Map<String, Object> getExtraData() {
        return Collections.emptyMap();
    }
}
