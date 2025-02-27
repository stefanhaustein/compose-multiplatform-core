/*
 * Copyright 2023 The Android Open Source Project
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

package androidx.compose.mpp.demo.textfield.android

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction

@Composable
fun ImeActionDemo() {
    LazyColumn {
        item { Item(ImeAction.None) }
        item { Item(ImeAction.Default) }
        item { Item(ImeAction.Go) }
        item { Item(ImeAction.Search) }
        item { Item(ImeAction.Send) }
        item { Item(ImeAction.Previous) }
        item { Item(ImeAction.Next) }
        item { Item(ImeAction.Done) }
    }
}

@Composable
private fun Item(imeAction: ImeAction) {
    TagLine(tag = "Ime Action: $imeAction")
    EditLine(imeAction = imeAction)
}
