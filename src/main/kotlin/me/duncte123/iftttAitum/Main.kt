package me.duncte123.iftttAitum

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.HttpResponseException
import io.javalin.http.UnauthorizedResponse
import io.javalin.http.bodyValidator
import me.duncte123.iftttAitum.ifttt.TestData
import me.duncte123.iftttAitum.ifttt.TriggerRequestBody

fun main() {
    val app = Javalin.create().start(8080)

    app.exception(HttpResponseException::class.java) { ex, ctx ->
        ctx.status(ex.status)

        val jackson = ObjectMapper()
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
        path("v1/ifttt") {
            before { ctx ->
                val key = ctx.header("IFTTT-Service-Key")

                if (key != System.getenv("SERVICE_KEY")) {
                    throw UnauthorizedResponse("Channel/Service key is not correct")
                }
            }
            get("status") { ctx ->
                ctx.status(200)
            }
            post("test/setup") { ctx ->
                ctx.json(TestData())
            }
            // eg app_trigger
            path("triggers/{trigger}") {
                get { it.status(200) }
                post { ctx ->
                    val body = ctx.bodyValidator<TriggerRequestBody>().get()
                    var limit = body.limit

                    if (limit == null || limit < 0) {
                        limit = 3
                    }


                    val jackson = ObjectMapper()
                    val data = jackson.createObjectNode()
                    val dataArr = jackson.createArrayNode()

                    if (limit > 0) {
                        // TODO: fetch data
                    }

                    data.set<ObjectNode>("data", dataArr)
                    ctx.json(data)
                }
            }
        }
    }
}
