package xyz.ivyxjc.pubg4j.web.entity

import xyz.ivyxjc.pubg4j.web.types.PlatformRegion
import java.util.*

/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
class PubgRoster : AbstractEntity {

    constructor()

    constructor(rosterId: String) {
        this.rosterId = rosterId
    }

    lateinit var rosterId: String

    var matchId: String? = null
    var shardId: PlatformRegion? = null
    var rank: Int? = null
    var teamId: Int? = null
    var win: Boolean? = null
    val participantList: List<PubgParticipant>

    init {
        participantList = ArrayList()
    }
}
