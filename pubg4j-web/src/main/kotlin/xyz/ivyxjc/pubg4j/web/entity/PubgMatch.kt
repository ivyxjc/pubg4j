package xyz.ivyxjc.pubg4j.web.entity

import xyz.ivyxjc.pubg4j.web.types.PlatformRegion

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */

class PubgMatch : AbstractEntity {
    constructor()

    constructor(matchId: String, playerId: String, shardId: PlatformRegion) {
        this.matchId = matchId
        this.playerId = playerId
        this.shardId = shardId
    }

    lateinit var matchId: String
    lateinit var playerId: String
    var shardId: PlatformRegion? = null
}
