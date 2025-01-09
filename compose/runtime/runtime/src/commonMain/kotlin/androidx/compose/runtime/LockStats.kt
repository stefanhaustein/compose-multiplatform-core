/*
 * Copyright 2024 The Android Open Source Project
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

package androidx.compose.runtime

/** Temporary place to gather locking performance data for benchmarks */
object LockStats {
    var lockCount = 0
    var nativeLockCount = 0
    var mutexAllocationCount = 0
    var mutexDeallocationCount = 0

    fun clear() {
        lockCount = 0
        nativeLockCount = 0
        mutexAllocationCount = 0
        mutexDeallocationCount = 0
    }

    override fun toString() = "count: $lockCount nativeCount: $nativeLockCount allocCount: $mutexAllocationCount deallocCount: $mutexDeallocationCount"
}