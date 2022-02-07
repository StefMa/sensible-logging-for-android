package co.m.common.utils.logging

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ProcessLifecycleOwner
import co.m.common.utils.logging.lifecycle.ActivityLifecycleLoggingInserter
import co.m.common.utils.logging.lifecycle.DEFAULT_SEPARATOR
import co.m.common.utils.logging.lifecycle.FragmentLifecycleLoggingInserter
import co.m.common.utils.logging.lifecycle.ProcessLifecycleLogger
import co.m.common.utils.logging.lifecycle.ServiceLifecycleLogger
import co.m.common.utils.logging.lifecycle.TYPE_ACTIVITY
import co.m.common.utils.logging.lifecycle.TYPE_FRAGMENT
import co.m.common.utils.logging.lifecycle.TYPE_PROCESS
import co.m.common.utils.logging.lifecycle.TYPE_SERVICE
import co.m.common.utils.logging.lifecycle.processIdentifier

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
