package xyz.ivyxjc.pubg4j.web.entity

import xyz.ivyxjc.pubg4j.web.types.PlatformRegion
import java.time.LocalDateTime
import java.util.*

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */

class PubgPlayer : AbstractEntity {

    constructor()

    constructor(playerId: String) {
        this.playerId = playerId
    }

    lateinit var playerId: String

    var createdAt: LocalDateTime? = null

    var name: String? = null

    /**
     * Platform-region shard
     */
    var shardId: PlatformRegion? = null

    /**
     * Identifies the studio and game
     */
    var titleId: String? = null

    var updatedAt: LocalDateTime? = null

    val matches: MutableList<PubgMatch>

    private val patchVersion: String? = null

    var stats: String? = null

    init {
        matches = ArrayList()
    }

    override fun toString(): String {
        return "PubgPlayer(playerId='$playerId', createdAt=$createdAt, name=$name, shardId=$shardId, titleId=$titleId, updatedAt=$updatedAt, matches=$matches, patchVersion=$patchVersion, stats=$stats)"
    }

}
