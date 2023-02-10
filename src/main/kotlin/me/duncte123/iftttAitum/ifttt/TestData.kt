/*
 *
 *     Aitum to IFTTT, trigger IFTTT actions with aitum!
 *     Copyright (C) 2023  Duncan Sterken
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

@file:Suppress("unused", "PropertyName")

package me.duncte123.iftttAitum.ifttt


class TestData {
    val samples = Samples()

    class Samples {
        val triggers = Triggers()
        val triggerFieldValidations = TriggerFieldValidations()

        // TODO: user_data
        class Triggers {
            val app_trigger = AppTrigger("FirstTriggerTestEver-v2")

            class AppTrigger(val trigger_identifier: String)
        }

        class TriggerFieldValidations {
            val app_trigger = AppTriggerValidation("FirstTriggerTestEver-v2", "test${System.currentTimeMillis()}test")

            class AppTriggerValidation(val valid: String, val invalid: String)
        }
    }
}
