/*
 * Copyright (C) 2018 The Android Open Source Project
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
@file:JvmName("SupportSQLiteDatabaseKt")

package androidx.sqlite.db

/**
 * Run [body] in a transaction marking it as successful if it completes without exception.
 *
 * @param exclusive Run in `EXCLUSIVE` mode when true, `IMMEDIATE` mode otherwise.
 * @param body Lambda to be run in the transaction.
 */
public inline fun <T> SupportSQLiteDatabase.transaction(
    exclusive: Boolean = true,
    body: SupportSQLiteDatabase.() -> T
): T {
    if (exclusive) {
        beginTransaction()
    } else {
        beginTransactionNonExclusive()
    }
    var cause: Throwable? = null
    try {
        return body()
    } catch (t: Throwable) {
        cause = t
        throw t
    } finally {
        if (cause == null) {
            setTransactionSuccessful()
        }
        endTransaction()
    }
}
