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

import com.mongodb.MongoClientSettings.getDefaultCodecRegistry
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import com.mongodb.client.model.Filters.`in` as filterIn
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider

// I want to be lazy so mongo it is
val mongoDb = createMongo()

fun findTriggerIdentityFromId(userIdentifier: String): String? {
    val links = mongoDb.getCollection("identity_links")

    val found = links.find(
        eq("identifier", userIdentifier)
    ).firstOrNull()

    return found?.getString("identity")
}

fun insertIdentityIfMissing(identity: String, userIdentifier: String) {
    val links = mongoDb.getCollection("identity_links")

    val foundLinks = links.find(and(
        eq("identifier", userIdentifier),
        eq("identity", identity)
    ))

    // isEmpty does not exist????
    @Suppress("ReplaceSizeZeroCheckWithIsEmpty")
    if (foundLinks.count() < 1){
        links.insertOne(
            Document()
                .append("identifier", userIdentifier)
                .append("identity", identity)
        )
    }
}

fun insertTrigger(triggerData: TriggerData) {
    val triggers = mongoDb.getCollection("triggers", TriggerData::class.java)

    triggers.insertOne(triggerData)
}

fun retrieveNewTriggers(limit: Int, delete: Boolean = true): List<TriggerData> {
    val triggers = mongoDb.getCollection("triggers", TriggerData::class.java)
    val foundTriggers = triggers.find().limit(limit)
    val collected = foundTriggers.toList()

    if (delete) {
        triggers.deleteMany(filterIn(
            "meta._id", collected.map { it.meta!!.id }
        ))
    }

    return collected
}


fun createMongo(): MongoDatabase {
    val pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build()
    val pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider))

    // TODO: env vars
    val mongoClient = MongoClients.create(System.getenv("MONGO_URI"))

    return mongoClient.getDatabase("aitum_ifttt").withCodecRegistry(pojoCodecRegistry)
}
