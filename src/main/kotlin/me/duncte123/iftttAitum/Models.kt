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

package me.duncte123.iftttAitum

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime
import java.util.*

val jackson = ObjectMapper()

class InsertTriggerRequest(
    val identifier: String,
    val userData: String?
)

class TriggerData(
    @JsonProperty("trigger_identifier") var identifier: String,
    @JsonProperty("user_data") var userData: String,
    @JsonProperty("created_at") var createdAt: LocalDateTime,
    @JsonProperty var meta: MetaData? = null
) {
    fun toJson(): JsonNode {
        val bytes = jackson.writeValueAsBytes(this)

        return jackson.readTree(bytes)
    }
}

class MetaData(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis() / 1000
)
