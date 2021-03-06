/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.kie.api.executor;

import java.util.List;
import java.util.function.Consumer;

public interface ExecutorStoreService {

    void persistRequest(RequestInfo request, Consumer<Object> function);

    void updateRequest(RequestInfo request, Consumer<Object> function);

    RequestInfo removeRequest(Long requestId, Consumer<Object> function);

    RequestInfo findRequest(Long id);
    
    List<RequestInfo> loadRequests();

    /**
     * load request that the scheduled timer are older that a certain amount in time units time
     * @param olderThan
     * @return a list of jobs overdue by certain amount of time
     */
    List<RequestInfo> loadRequestsOlderThan(long olderThan); 

    void persistError(ErrorInfo error);

    void updateError(ErrorInfo error);

    ErrorInfo removeError(Long errorId);

    ErrorInfo findError(Long id);

}
