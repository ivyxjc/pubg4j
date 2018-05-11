package xyz.ivyxjc.pubg4j.web.entity

import xyz.ivyxjc.pubg4j.web.types.PlatformRegion


/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
class PubgParticipant : AbstractEntity {

    constructor()

    constructor(participantId: String) {
        this.participantId = participantId
    }

    lateinit var participantId: String

    var matchId: String? = null

    var rosterId: String? = null


    var shardId: PlatformRegion? = null

    var dbnos: Int? = null

    var assists: Int? = null

    var boosts: Int? = null

    var damageDealt: Double? = null

    var deathType: String? = null

    var headshotKills: Int? = null

    var heals: Int? = null

    var killPlace: Int? = null

    var killPoints: Int? = null

    var killPointsDelta: Double? = null

    var killStreaks: Int? = null

    var kills: Int? = null

    var lastKillPoints: Int? = null

    var lastWinPoints: Int? = null

    var longestKill: Double? = null

    var mostDamage: Int? = null

    var name: String? = null

    var playerId: String? = null

    var revives: Int? = null

    var rideDistance: Double? = null

    var roadKills: Int? = null

    var teamKills: Int? = null

    var timeSurvived: Int? = null

    var vehicleDestroys: Int? = null

    var walkDistance: Double? = null

    var weaponsAcquired: Int? = null

    var winPlace: Int? = null

    var winPoints: Int? = null

    var winPointsDelta: Double? = null

}
