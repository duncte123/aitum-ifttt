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

import com.fasterxml.jackson.databind.node.ObjectNode
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.*
import me.duncte123.iftttAitum.ifttt.TestData
import me.duncte123.iftttAitum.ifttt.TriggerRequestBody
import java.time.LocalDateTime

// TODO:
//  - Set up database (sqlite)
//  - Figure out how to use ingredients
fun main() {
    val app = Javalin.create().start(8080)

    app.exception(HttpResponseException::class.java) { ex, ctx ->
        ctx.status(ex.status)

        val obj = jackson.createObjectNode()
        val errArr = jackson.createArrayNode()

        errArr.add(
            jackson.createObjectNode()
                .put("message", ex.message)
        )

        obj.set<ObjectNode>("errors", errArr)

        ctx.json(obj)
    }

    app.routes {
        post("insert-trigger") { ctx ->
            val trig = ctx.bodyValidator<InsertTriggerRequest>()
                .check(
                    "identifier",
                    { it.identifier != null && it.identifier.length > 10 },
                    "identifier must be set and longer than 10 chars"
                )
                .check(
                    "userData",
                    { it.userData == null || it.userData.isNotBlank() },
                    "User data must not be empty if set"
                )
                .get()

            val data = TriggerData(
                trig.identifier,
                trig.userData ?: "",
                LocalDateTime.now(),
                MetaData()
            )

            receivedTriggers[data.identifier] = data

            ctx.status(HttpStatus.CREATED)
        }
        path("v1/ifttt") {
            before { ctx ->
                val key = ctx.header("IFTTT-Service-Key")

                if (key != System.getenv("IFTTT_SERVICE_KEY")) {
                    throw UnauthorizedResponse("Channel/Service key is not correct")
                }
            }
            get("status") { ctx ->
                ctx.status(HttpStatus.OK)
            }
            post("test/setup") { ctx ->
                ctx.json(TestData())
            }
            // eg app_trigger
            path("triggers/{trigger}") {
                get { it.status(HttpStatus.OK) }
                post { ctx ->
                    if (ctx.pathParam("trigger") != "app_trigger") {
                        throw NotFoundResponse()
                    }

                    val body = ctx.bodyValidator<TriggerRequestBody>().get()
                    var limit = body.limit

                    if (limit == null || limit < 0) {
                        limit = 3
                    }

                    val data = jackson.createObjectNode()
                    val dataArr = jackson.createArrayNode()

                    if (limit > 0) {
                        retrieveNewTriggers(limit)
                            .forEach { dataArr.add(it.toJson()) }
                    }

                    data.set<ObjectNode>("data", dataArr)
                    ctx.json(data)
                }
            }
        }
    }
}
