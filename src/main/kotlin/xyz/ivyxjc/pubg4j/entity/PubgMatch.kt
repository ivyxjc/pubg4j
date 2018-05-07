package xyz.ivyxjc.pubg4j.entity

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */

class PubgMatch {
    constructor()

    constructor(matchId: String, playerId: String) {
        this.matchId = matchId
        this.playerId = playerId
    }

    lateinit var matchId: String
    lateinit var playerId: String
}
