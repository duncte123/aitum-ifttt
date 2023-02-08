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
import java.time.LocalDateTime
import java.util.UUID

val receivedTriggers = mutableMapOf<String, TriggerData>()

class TriggerData(
    @JsonProperty("trigger_identifier") var identifier: String,
    @JsonProperty("created_at") var createdAt: LocalDateTime,
    var meta: MetaData? = null
)

class MetaData(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis() / 1000
)
