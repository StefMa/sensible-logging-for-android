/*
 * Copyright 2022 Volvo Car Mobility AB
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

package com.sensiblelogging

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ProcessLifecycleOwner
import com.sensiblelogging.lifecycle.ActivityLifecycleLoggingInserter
import com.sensiblelogging.lifecycle.DEFAULT_SEPARATOR
import com.sensiblelogging.lifecycle.FragmentLifecycleLoggingInserter
import com.sensiblelogging.lifecycle.ProcessLifecycleLogger
import com.sensiblelogging.lifecycle.ServiceLifecycleLogger
import com.sensiblelogging.lifecycle.TYPE_ACTIVITY
import com.sensiblelogging.lifecycle.TYPE_FRAGMENT
import com.sensiblelogging.lifecycle.TYPE_PROCESS
import com.sensiblelogging.lifecycle.TYPE_SERVICE
import com.sensiblelogging.lifecycle.processIdentifier

fun Int.toLogLevel(): Level =
    when (this) {
        Log.VERBOSE -> Level.VERBOSE
        Log.DEBUG -> Level.DEBUG
        Log.INFO -> Level.INFO
        Log.WARN -> Level.WARN
        Log.ERROR -> Level.ERROR
        Log.ASSERT -> Level.ASSERT
        else -> throw IllegalStateException("Unknown priority: $this")
    }

fun Level.toLogPriority(): Int =
    when (this) {
        Level.VERBOSE -> Log.VERBOSE
        Level.DEBUG -> Log.DEBUG
        Level.INFO -> Log.INFO
        Level.WARN -> Log.WARN
        Level.ERROR -> Log.ERROR
        Level.ASSERT -> Log.ASSERT
    }

/**
 * Should be performed in the onCreate() method of Application lifecycle
 */
fun Application.registerLifecycleLoggers(
    processCategory: String = TYPE_PROCESS,
    activityCategory: String = TYPE_ACTIVITY,
    fragmentCategory: String = TYPE_FRAGMENT,
    separator: String = DEFAULT_SEPARATOR
) {
    ProcessLifecycleOwner.get().lifecycle.addObserver(ProcessLifecycleLogger(processCategory, processIdentifier()))
    this.registerActivityLifecycleCallbacks(ActivityLifecycleLoggingInserter(activityCategory, separator))
    this.registerActivityLifecycleCallbacks(FragmentLifecycleLoggingInserter(fragmentCategory, separator))
}

/**
 * Should be performed in the onCreate() method of Service lifecycle
 */
fun LifecycleService.registerLifecycleLogger(category: String = TYPE_SERVICE, separator: String = DEFAULT_SEPARATOR) {
    lifecycle.addObserver(ServiceLifecycleLogger(this, category, separator))
}
