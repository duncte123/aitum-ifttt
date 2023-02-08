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
