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

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.codecs.pojo.annotations.*
import java.time.LocalDateTime
import java.util.*

val jackson = ObjectMapper()

@JsonIgnoreProperties(ignoreUnknown = true)
class InsertTriggerRequest(
    val identifier: String,
    val userData: String?
)

class TriggerData @BsonCreator constructor (
    @JsonProperty("trigger_identifier") @BsonProperty("identifier") var identifier: String,
    @JsonProperty("user_data") @BsonProperty("userData") var userData: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'") @JsonProperty("created_at") @BsonProperty("createdAt") var createdAt: LocalDateTime,
    @JsonProperty @BsonProperty("meta") var meta: MetaData? = null
)

class MetaData @BsonCreator constructor (
    @BsonId val id: String,
    @BsonProperty("timestamp") val timestamp: Long,
    @BsonProperty("triggerIdentity") val triggerIdentity: String
) {
    // Must do manual constructor here
    constructor(): this(
        UUID.randomUUID().toString(),
        System.currentTimeMillis() / 1000,
        ""
    )
}
