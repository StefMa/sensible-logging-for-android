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

package com.sensiblelogging.util

import com.sensiblelogging.printer.LogCatChannel

object Constants {
    const val DEFAULT_CATEGORY = "Default"
    internal val DEFAULT_CATEGORY_VARARG = arrayOf(DEFAULT_CATEGORY)
    internal val DEFAULT_CATEGORIES: List<String> = listOf(DEFAULT_CATEGORY)
    internal val DEFAULT_CHANNELS = arrayOf(LogCatChannel.id)
    internal val DEFAULT_CHANNEL_LIST = DEFAULT_CHANNELS.asList()
    internal val EMPTY_PARAMS: Map<String, String> = emptyMap()
    internal const val DEFAULT_STACK_DEPTH = 4
}
