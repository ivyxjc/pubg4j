package xyz.ivyxjc.pubg4j.entity

import xyz.ivyxjc.pubg4j.types.GameMode
import xyz.ivyxjc.pubg4j.types.MapName
import xyz.ivyxjc.pubg4j.types.PlatformRegion
import java.time.LocalDateTime
import java.util.*

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
class PubgMatchDetail {

    constructor()

    constructor(matchId: String) {
        this.matchId = matchId
    }

    lateinit var matchId: String
    /**
     * Time this match object was stored in the API
     */
    var createdAt: LocalDateTime? = null

    /**
     * Length of the match
     */
    var duration: Int? = null

    /**
     * Game mode played
     */
    var gameMode: GameMode? = null

    /**
     * Map name
     */
    var mapName: MapName? = null

    /**
     * Platform-region shard
     */
    var shardId: PlatformRegion? = null

    /**
     * Identifies the studio and game
     */
    var titleId: String? = null

    val rosterMap: MutableMap<String, PubgRoster>

    val participantDetailMap: MutableMap<String, PubgParticipant>

    private val patchVersion: String? = null

    private val stats: String? = null

    private val tags: String? = null

    init {
        rosterMap = HashMap()
        participantDetailMap = HashMap()
    }
}
