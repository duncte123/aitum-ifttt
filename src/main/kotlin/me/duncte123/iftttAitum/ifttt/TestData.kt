@file:Suppress("unused", "PropertyName")

package me.duncte123.iftttAitum.ifttt

class TestData {
    val samples = Samples()

    class Samples {
        val triggers = Triggers()
        val triggerFieldValidations = TriggerFieldValidations()

        // TODO: user_data
        class Triggers {
            val app_trigger = AppTrigger("FirstTriggerTestEver")

            class AppTrigger(val trigger_identifier: String)
        }

        class TriggerFieldValidations {
            val app_trigger = AppTriggerValidation("FirstTriggerTestEver", "test${System.currentTimeMillis()}test")

            class AppTriggerValidation(val valid: String, val invalid: String)
        }
    }
}
