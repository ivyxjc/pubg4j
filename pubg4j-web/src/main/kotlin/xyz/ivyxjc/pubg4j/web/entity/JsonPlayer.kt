package xyz.ivyxjc.pubg4j.web.entity

import org.apache.commons.lang3.builder.ToStringBuilder
import org.springframework.util.Assert
import xyz.ivyxjc.pubg4j.web.types.PlatformRegion
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class JsonPlayer {
    /**
     * data'size is one
     */
    lateinit var data: MutableList<JsonData>
    lateinit var links: JsonLinks


    fun buildPubgPlayer(): PubgPlayer {
        Assert.notNull(data, "JsonPlayer parser should not be null")
        Assert.notEmpty(data, "JsonPlayer data should not be empty")
        val firstData = data[0]
        val pubgPlayer = PubgPlayer()
        pubgPlayer.playerId = data[0].id

        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        pubgPlayer.createdAt = LocalDateTime.parse(firstData.attributes.createdAt, dateTimeFormatter)
        pubgPlayer.updatedAt = LocalDateTime.parse(firstData.attributes.updatedAt, dateTimeFormatter)
        pubgPlayer.name = firstData.attributes.name
        pubgPlayer.shardId = PlatformRegion.enumOf(firstData.attributes.shardId)
        pubgPlayer.titleId = firstData.attributes.titleId
        pubgPlayer.stats = firstData.attributes.stats
        firstData.relationships.matches.data.forEach { item ->
            val match = PubgMatch()
            match.matchId = item.id
            match.playerId = pubgPlayer.playerId
            match.shardId = pubgPlayer.shardId
            pubgPlayer.matches.add(match)
        }
        return pubgPlayer
    }

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this)
    }
}

class JsonLinks {
    lateinit var self: String
}

class JsonData {
    /**
     *for player, always be 'player'
     */
    lateinit var type: String
    /**
     *playerId
     */
    lateinit var id: String

    lateinit var attributes: JsonAttributes

    lateinit var relationships: JsonRelationShip

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this)
    }
}

class JsonAttributes {
    /**
     * player name
     */
    lateinit var name: String
    var stats: String? = null
    /**
     * always be 'bluehole-pubg'
     */
    lateinit var titleId: String
    /**
     * like 'steam'
     */
    lateinit var shardId: String
    lateinit var createdAt: String
    lateinit var updatedAt: String
    var patchVersion: String? = null

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this)
    }
}

class JsonRelationShip {
    lateinit var matches: JsonMatch
    override fun toString(): String {
        return matches.toString();
    }
}

class JsonMatch {
    lateinit var data: MutableList<JsonMatchData>
    override fun toString(): String {
        return data.toString()
    }
}

class JsonMatchData {
    lateinit var type: String
    lateinit var id: String

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this)
    }
}


