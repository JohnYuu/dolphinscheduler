/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.server.master.event;

import org.apache.dolphinscheduler.common.enums.StateEventType;
import org.apache.dolphinscheduler.server.master.runner.WorkflowExecuteRunnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.auto.service.AutoService;

@AutoService(StateEventHandler.class)
public class TaskWaitTaskGroupStateHandler implements StateEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(TaskWaitTaskGroupStateHandler.class);

    @Override
    public boolean handleStateEvent(WorkflowExecuteRunnable workflowExecuteRunnable,
                                    StateEvent stateEvent) {
        logger.info("Handle task instance wait task group event, taskInstanceId: {}", stateEvent.getTaskInstanceId());
        if (workflowExecuteRunnable.checkForceStartAndWakeUp(stateEvent)) {
            logger.info("Success wake up task instance, taskInstanceId: {}", stateEvent.getTaskInstanceId());
        } else {
            logger.info("Failed to wake up task instance, taskInstanceId: {}", stateEvent.getTaskInstanceId());
        }
        return true;
    }

    @Override
    public StateEventType getEventType() {
        return StateEventType.WAKE_UP_TASK_GROUP;
    }
}
