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

package org.apache.streampark.console.core.enums;

import lombok.Getter;

/** Describe the status of Spark Application */
@Getter
public enum SparkAppStateEnum {

    /** Added new job to database. */
    ADDED(0),

    /**
     * The job has been received by the Dispatcher, and is waiting for the job manager to be created.
     */
    INITIALIZING(1),

    /** Job is newly created, no task has started to run. */
    CREATED(2),

    /** Application which is currently running. */
    STARTING(3),

    /** Application which is currently running. */
    RESTARTING(4),

    /** Some tasks are scheduled or running, some may be pending, some may be finished. */
    RUNNING(5),

    /** The job has failed and is currently waiting for the cleanup to complete. */
    FAILING(6),

    /** The job has failed with a non-recoverable task failure. */
    FAILED(7),

    /** Job is being cancelled. */
    CANCELLING(8),

    /** Job has been cancelled. */
    CANCELED(9),

    /** All the job's tasks have successfully finished. */
    FINISHED(10),

    /**
     * The job has been suspended which means that it has been stopped but not been removed from a
     * potential HA job store.
     */
    SUSPENDED(11),

    /** The job is currently reconciling and waits for task execution report to recover state. */
    RECONCILING(12),

    /** Loss of mapping. */
    LOST(13),

    /** Mapping. */
    MAPPING(14),

    /** Other statuses. */
    OTHER(15),

    /** Has rollback. */
    REVOKED(16),

    /**
     * Lost track of Spark job temporarily. A complete loss of Spark job tracking translates into LOST
     * state.
     */
    @Deprecated
    SILENT(17),

    /** Spark job has terminated vaguely, maybe FINISHED, CANCELED or FAILED. */
    TERMINATED(18),

    /** Spark job has terminated vaguely, maybe FINISHED, CANCELED or FAILED. */
    @Deprecated
    POS_TERMINATED(19),

    /** Job SUCCEEDED on yarn. */
    SUCCEEDED(20),

    /** Has killed in Yarn. */
    KILLED(-9);

    private final int value;

    SparkAppStateEnum(int value) {
        this.value = value;
    }

    public static SparkAppStateEnum of(Integer state) {
        for (SparkAppStateEnum appState : values()) {
            if (appState.value == state) {
                return appState;
            }
        }
        return SparkAppStateEnum.OTHER;
    }

    public static SparkAppStateEnum of(String name) {
        for (SparkAppStateEnum appState : values()) {
            if (appState.name().equals(name)) {
                return appState;
            }
        }
        return SparkAppStateEnum.OTHER;
    }

    public static boolean isEndState(Integer appState) {
        SparkAppStateEnum sparkAppStateEnum = SparkAppStateEnum.of(appState);
        return SparkAppStateEnum.CANCELED == sparkAppStateEnum
            || SparkAppStateEnum.FAILED == sparkAppStateEnum
            || SparkAppStateEnum.KILLED == sparkAppStateEnum
            || SparkAppStateEnum.FINISHED == sparkAppStateEnum
            || SparkAppStateEnum.SUCCEEDED == sparkAppStateEnum
            || SparkAppStateEnum.LOST == sparkAppStateEnum
            || SparkAppStateEnum.TERMINATED == sparkAppStateEnum;
    }

    public static boolean isLost(Integer appState) {
        SparkAppStateEnum sparkAppStateEnum = SparkAppStateEnum.of(appState);
        return SparkAppStateEnum.LOST == sparkAppStateEnum;
    }
}
