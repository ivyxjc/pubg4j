package xyz.ivyxjc.pubg4j

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import xyz.ivyxjc.pubg4j.entity.*
import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException
import xyz.ivyxjc.pubg4j.types.GameMode
import xyz.ivyxjc.pubg4j.types.MapName
import xyz.ivyxjc.pubg4j.types.PlatformRegion
import xyz.ivyxjc.pubg4j.utils.JsonConstants
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * @author Ivyxjc
 * @since 4/22/2018
 */
@Service
class JsonBuilder {

    @Throws(UnsupportedPubgElementException::class)
    fun buildPlayer(`in`: InputStream): PubgPlayer {
        val jsonReader = InputStreamReader(`in`)
        val map = gson.fromJson(jsonReader, HashMap::class.java)
        var dataMap: LinkedTreeMap<*, *>?
        var attributeMap: LinkedTreeMap<*, *>? = null
        dataMap = (map["data"] as List<*>)[0] as LinkedTreeMap<*, *>
        Assert.notNull(dataMap, "Player's data map should not be null")
        if (dataMap["attributes"] is LinkedTreeMap<*, *>) {
            attributeMap = dataMap["attributes"] as LinkedTreeMap<*, *>
        }
        Assert.notNull(attributeMap, "Player's attributea Map should not be null")
        val pubgPlayer = PubgPlayer(dataMap["id"] as String)
        pubgPlayer.createdAt = LocalDateTime.parse(attributeMap!!["createdAt"] as String, dateTimeFormatter)
        pubgPlayer.name = attributeMap["name"] as String
        pubgPlayer.shardId = PlatformRegion.enumOf(attributeMap["shardId"] as String)
        pubgPlayer.titleId = attributeMap["titleId"] as String
        pubgPlayer.updatedAt = LocalDateTime.parse(attributeMap["updatedAt"] as String, dateTimeFormatter)
        val matchList = ((dataMap["relationships"] as LinkedTreeMap<*, *>)["matches"] as LinkedTreeMap<*, *>)["data"] as ArrayList<*>
        for (i in matchList.indices) {
            if (matchList[i] is LinkedTreeMap<*, *>) {
                val ltm = matchList[i] as LinkedTreeMap<*, *>
                if (ltm.size < 2) {
                    continue
                }
                val pubgMatch = PubgMatch(ltm["id"] as String, pubgPlayer.playerId, pubgPlayer.shardId!!)
                val ltmId = ltm["id"] as String
                Assert.notNull(ltmId, "Player's data's match id should not be nul")
                pubgPlayer.matches.add(pubgMatch)
            }
        }
        return pubgPlayer
    }

    @Throws(UnsupportedPubgElementException::class)
    fun buildMatch(`in`: InputStream): PubgMatchDetail {
        val map = gson.fromJson(InputStreamReader(`in`), HashMap::class.java)
        val dataMap = map["data"] as LinkedTreeMap<*, *>
        Assert.notNull(dataMap, "dataMap should not be null")
        val attributesMap = dataMap["attributes"] as LinkedTreeMap<*, *>
        Assert.notNull(attributesMap, "attributesMap should not be null")
        val relationshipsMap = dataMap["relationships"] as LinkedTreeMap<*, *>
        Assert.notNull(relationshipsMap, "relationshipsMap should not be null")
        val rosterMapList = (relationshipsMap["rosters"] as LinkedTreeMap<*, *>)["data"] as ArrayList<*>
        Assert.notNull(rosterMapList, "rosterMapList should not be null")
        val includedList = map["included"] as ArrayList<*>
        Assert.notNull(includedList, "includedList should not be null")
        val matchDetail = PubgMatchDetail(dataMap["id"] as String)
        matchDetail.createdAt = LocalDateTime.parse(attributesMap["createdAt"] as String, dateTimeFormatter)
        matchDetail.duration = (attributesMap["duration"] as Double).toInt()
        matchDetail.gameMode = GameMode.enumOf(attributesMap["gameMode"] as String)
        matchDetail.mapName = MapName.enumOf(attributesMap["mapName"] as String)
        matchDetail.shardId = PlatformRegion.enumOf(attributesMap["shardId"] as String)
        matchDetail.titleId = attributesMap["titleId"] as String
        for (i in rosterMapList.indices) {
            val ltm = rosterMapList[i] as LinkedTreeMap<*, *>
            Assert.notNull(includedList, "ltm should not be null")
            val tmpId = ltm["id"] as String
            Assert.notNull(tmpId, "ltm.get(id) should not be null")
            val roster = PubgRoster(tmpId)
            matchDetail.rosterMap.put(roster.rosterId, roster)
        }

        for (i in includedList.indices) {
            if (includedList[i] is LinkedTreeMap<*, *>) {
                val ltm = includedList[i] as LinkedTreeMap<*, *>
                if (JsonConstants.TYPE_PARTICIPANT == ltm["type"]) {
                    val tmpId = ltm["id"] as String
                    Assert.notNull(tmpId, "participant's id should not be null")
                    val participantDetail = matchDetail.participantDetailMap.get(tmpId)
                        ?: PubgParticipant(tmpId)
                    val statsLtm = (ltm["attributes"] as LinkedTreeMap<*, *>)["stats"] as LinkedTreeMap<*, *>
                    parseParticipant(participantDetail, statsLtm)
                    participantDetail.matchId = matchDetail.matchId
                    matchDetail.participantDetailMap
                        .putIfAbsent(participantDetail.participantId, participantDetail)

                }
                if (JsonConstants.TYPE_ROSTER == ltm["type"]) {
                    val tmpRosterId = ltm["id"] as String
                    Assert.notNull(tmpRosterId, "roster's id should not be null")
                    val roster = matchDetail.rosterMap.get(tmpRosterId)
                    val atbLtm = ltm["attributes"] as LinkedTreeMap<*, *>
                    val statsLtm = atbLtm["stats"] as LinkedTreeMap<*, *>
                    roster?.matchId = matchDetail.matchId
                    roster?.shardId = PlatformRegion.enumOf(atbLtm["shardId"] as String)
                    roster?.win = "true" == atbLtm["won"]
                    roster?.rank = (statsLtm["rank"] as Double).toInt()
                    roster?.teamId = (statsLtm["teamId"] as Double).toInt()
                    val rlsDataList = ((ltm["relationships"] as LinkedTreeMap<*, *>)["participants"] as LinkedTreeMap<*, *>)["data"] as List<*>
                    for (j in rlsDataList.indices) {
                        val rlsDataLtm = rlsDataList[j] as LinkedTreeMap<*, *>
                        val tmpParId = rlsDataLtm["id"] as String
                        if (matchDetail.participantDetailMap.containsKey(tmpParId)) {
                            matchDetail.participantDetailMap
                                .get(tmpParId)
                                ?.rosterId = tmpRosterId
                        } else {
                            val participantDetail = PubgParticipant(tmpParId)
                            participantDetail.rosterId = tmpRosterId
                            matchDetail.participantDetailMap.put(tmpParId, participantDetail)
                        }
                    }
                }
            }
        }
        return matchDetail
    }

    private fun parseParticipant(participant: PubgParticipant, treeMap: LinkedTreeMap<*, *>) {
        participant.dbnos = (treeMap["DBNOs"] as Double).toInt()
        participant.assists = (treeMap["assists"] as Double).toInt()
        participant.boosts = (treeMap["boosts"] as Double).toInt()
        participant.damageDealt = treeMap["damageDealt"] as Double
        participant.deathType = treeMap["deathType"] as String
        participant.headshotKills = (treeMap["headshotKills"] as Double).toInt()
        participant.heals = (treeMap["heals"] as Double).toInt()
        participant.killPlace = (treeMap["killPlace"] as Double).toInt()
        participant.killPoints = (treeMap["killPoints"] as Double).toInt()
        participant.killPointsDelta = treeMap["killPointsDelta"] as Double
        participant.killStreaks = (treeMap["killStreaks"] as Double).toInt()
        participant.kills = (treeMap["kills"] as Double).toInt()
        participant.lastKillPoints = (treeMap["lastKillPoints"] as Double).toInt()
        participant.lastWinPoints = (treeMap["lastWinPoints"] as Double).toInt()
        participant.longestKill = treeMap["longestKill"] as Double
        participant.mostDamage = (treeMap["mostDamage"] as Double).toInt()
        participant.name = treeMap["name"] as String
        participant.playerId = treeMap["playerId"] as String
        participant.revives = (treeMap["revives"] as Double).toInt()
        participant.rideDistance = treeMap["rideDistance"] as Double
        participant.roadKills = (treeMap["roadKills"] as Double).toInt()
        participant.teamKills = (treeMap["teamKills"] as Double).toInt()
        participant.timeSurvived = (treeMap["timeSurvived"] as Double).toInt()
        participant.vehicleDestroys = (treeMap["vehicleDestroys"] as Double).toInt()
        participant.walkDistance = treeMap["walkDistance"] as Double
        participant.weaponsAcquired = (treeMap["weaponsAcquired"] as Double).toInt()
        participant.winPlace = (treeMap["winPlace"] as Double).toInt()
        participant.winPoints = (treeMap["winPoints"] as Double).toInt()
        participant.winPointsDelta = treeMap["winPointsDelta"] as Double
    }

    companion object {
        // DateTimeFormatter is immutable and thread-safe.
        private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

        // Gson is thread-safe
        private val gson = Gson()
    }
}
