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

package com.sensiblelogging.lifecycle

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

internal class ActivityLifecycleLoggingInserter(private val category: String, private val separator: String) :
    ActivityOnCreateListener(doOnCreate = {
        it.lifecycle.addObserver(
            ActivityLifecycleLogger(it, category, separator)
        )
    })

internal class FragmentLifecycleLoggingInserter(private val category: String, private val separator: String) :
    ActivityOnCreateListener(doOnCreate = {
        it.supportFragmentManager.registerFragmentLifecycleCallbacks(
            FragmentLifecycleLogger(category, separator),
            true
        )
    })

internal abstract class ActivityOnCreateListener(private val doOnCreate: (FragmentActivity) -> Unit) :
    SimpleActivityLifeCycleCallbacks() {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        (activity as? FragmentActivity)?.apply {
            doOnCreate.invoke(this)
        }
    }
}
