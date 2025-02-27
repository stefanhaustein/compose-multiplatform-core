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

package androidx.tv.material3

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.testutils.assertAgainstGolden
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.test.filters.MediumTest
import androidx.test.filters.SdkSuppress
import androidx.test.screenshot.AndroidXScreenshotTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@MediumTest
@RunWith(Parameterized::class)
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.O)
@OptIn(ExperimentalTestApi::class, ExperimentalTvMaterial3Api::class)
class CheckboxScreenshotTest(private val scheme: ColorSchemeWrapper) {

    @get:Rule val rule = createComposeRule()

    @get:Rule val screenshotRule = AndroidXScreenshotTestRule(TV_GOLDEN_MATERIAL3)

    val wrap = Modifier.wrapContentSize(Alignment.TopStart)

    private val wrapperTestTag = "checkboxWrapper"

    @Test
    fun checkBox_checked() {
        rule.setMaterialContent(scheme.colorScheme) {
            Box(wrap.testTag(wrapperTestTag)) { Checkbox(checked = true, onCheckedChange = {}) }
        }
        assertToggeableAgainstGolden("checkBox_${scheme.name}_checked")
    }

    @Test
    fun checkBox_unchecked() {
        rule.setMaterialContent(scheme.colorScheme) {
            Box(wrap.testTag(wrapperTestTag)) {
                Checkbox(modifier = wrap, checked = false, onCheckedChange = {})
            }
        }
        assertToggeableAgainstGolden("checkBox_${scheme.name}_unchecked")
    }

    @Test
    fun checkBox_pressed() {
        rule.setMaterialContent(scheme.colorScheme) {
            Box(wrap.testTag(wrapperTestTag)) {
                Checkbox(modifier = wrap, checked = false, onCheckedChange = {})
            }
        }

        rule.mainClock.autoAdvance = false
        rule.onNode(isToggleable()).performKeyInput { pressKey(Key.DirectionCenter) }

        rule.mainClock.advanceTimeByFrame()
        rule.waitForIdle() // Wait for measure
        rule.mainClock.advanceTimeBy(milliseconds = 200)

        assertToggeableAgainstGolden("checkBox_${scheme.name}_pressed")
    }

    @Test
    fun checkBox_indeterminate() {
        rule.setMaterialContent(scheme.colorScheme) {
            Box(wrap.testTag(wrapperTestTag)) {
                TriStateCheckbox(
                    state = ToggleableState.Indeterminate,
                    modifier = wrap,
                    onClick = {}
                )
            }
        }
        assertToggeableAgainstGolden("checkBox_${scheme.name}_indeterminate")
    }

    @Test
    fun checkBox_disabled_checked() {
        rule.setMaterialContent(scheme.colorScheme) {
            Box(wrap.testTag(wrapperTestTag)) {
                Checkbox(modifier = wrap, checked = true, enabled = false, onCheckedChange = {})
            }
        }
        assertToggeableAgainstGolden("checkBox_${scheme.name}_disabled_checked")
    }

    @Test
    fun checkBox_disabled_unchecked() {
        rule.setMaterialContent(scheme.colorScheme) {
            Box(wrap.testTag(wrapperTestTag)) {
                Checkbox(modifier = wrap, checked = false, enabled = false, onCheckedChange = {})
            }
        }
        assertToggeableAgainstGolden("checkBox_${scheme.name}_disabled_unchecked")
    }

    @Test
    fun checkBox_disabled_indeterminate() {
        rule.setMaterialContent(scheme.colorScheme) {
            Box(wrap.testTag(wrapperTestTag)) {
                TriStateCheckbox(
                    state = ToggleableState.Indeterminate,
                    enabled = false,
                    modifier = wrap,
                    onClick = {}
                )
            }
        }
        assertToggeableAgainstGolden("checkBox_${scheme.name}_disabled_indeterminate")
    }

    @Test
    fun checkBox_unchecked_animateToChecked() {
        val isChecked = mutableStateOf(false)
        rule.setMaterialContent(scheme.colorScheme) {
            Box(wrap.testTag(wrapperTestTag)) {
                Checkbox(
                    modifier = wrap,
                    checked = isChecked.value,
                    onCheckedChange = { isChecked.value = it }
                )
            }
        }

        rule.mainClock.autoAdvance = false

        rule.runOnIdle { isChecked.value = true }

        rule.mainClock.advanceTimeByFrame()
        rule.waitForIdle() // Wait for measure
        rule.mainClock.advanceTimeBy(milliseconds = 80)

        assertToggeableAgainstGolden("checkBox_${scheme.name}_unchecked_animateToChecked")
    }

    // @Test // b/328436857
    fun checkBox_checked_animateToUnchecked() {
        val isChecked = mutableStateOf(true)
        rule.setMaterialContent(scheme.colorScheme) {
            Box(wrap.testTag(wrapperTestTag)) {
                Checkbox(
                    modifier = wrap,
                    checked = isChecked.value,
                    onCheckedChange = { isChecked.value = it }
                )
            }
        }

        rule.mainClock.autoAdvance = false

        rule.runOnIdle { isChecked.value = false }

        rule.mainClock.advanceTimeByFrame()
        rule.waitForIdle() // Wait for measure
        rule.mainClock.advanceTimeBy(milliseconds = 80)

        assertToggeableAgainstGolden("checkBox_${scheme.name}_checked_animateToUnchecked")
    }

    @Test
    fun checkBox_hover() {
        rule.setMaterialContent(scheme.colorScheme) {
            Box(wrap.testTag(wrapperTestTag)) {
                Checkbox(modifier = wrap, checked = true, onCheckedChange = {})
            }
        }

        rule.onNode(isToggleable()).performMouseInput { enter(center) }

        rule.waitForIdle()

        assertToggeableAgainstGolden("checkBox_${scheme.name}_hover")
    }

    @Test
    fun checkBox_focus() {
        val focusRequester = FocusRequester()
        var localInputModeManager: InputModeManager? = null

        rule.setMaterialContent(scheme.colorScheme) {
            localInputModeManager = LocalInputModeManager.current
            Box(wrap.testTag(wrapperTestTag)) {
                Checkbox(
                    modifier = wrap.focusRequester(focusRequester),
                    checked = true,
                    onCheckedChange = {}
                )
            }
        }

        rule.runOnIdle {
            localInputModeManager!!.requestInputMode(InputMode.Keyboard)
            focusRequester.requestFocus()
        }

        rule.waitForIdle()

        assertToggeableAgainstGolden("checkBox_${scheme.name}_focus")
    }

    private fun assertToggeableAgainstGolden(goldenName: String) {
        // TODO: replace with find(isToggeable()) after b/157687898 is fixed
        rule
            .onNodeWithTag(wrapperTestTag)
            .captureToImage()
            .assertAgainstGolden(screenshotRule, goldenName)
    }

    // Provide the ColorScheme and their name parameter in a ColorSchemeWrapper.
    // This makes sure that the default method name and the initial Scuba image generated
    // name is as expected.
    companion object {
        @OptIn(ExperimentalTvMaterial3Api::class)
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun parameters() =
            arrayOf(
                ColorSchemeWrapper("lightTheme", lightColorScheme()),
                ColorSchemeWrapper("darkTheme", darkColorScheme()),
            )
    }

    @OptIn(ExperimentalTvMaterial3Api::class)
    class ColorSchemeWrapper constructor(val name: String, val colorScheme: ColorScheme) {
        override fun toString(): String {
            return name
        }
    }
}
