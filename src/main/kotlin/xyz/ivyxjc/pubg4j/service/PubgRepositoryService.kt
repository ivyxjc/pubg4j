package xyz.ivyxjc.pubg4j.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.ivyxjc.pubg4j.dao.*
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail
import xyz.ivyxjc.pubg4j.entity.PubgPlayer
import xyz.ivyxjc.pubg4j.entity.PubgRoster

/**
 * @author Ivyxjc
 * @since 5/7/2018
 */

interface PubgMatchRepoService {
    fun insertPubgMatch(matchDetail: PubgMatchDetail)
    fun deleteAllByMatchId(matchId: String)
    fun queryByMatchId(matchId: String): PubgMatchDetail?
}

@Service
open class PubgMatchRepoServiceImpl : PubgMatchRepoService {

    @Autowired
    private lateinit var mPubgMatchDetailMapper: PubgMatchDetailMapper

    @Autowired
    private lateinit var mPubgRosterMapper: PubgRosterMapper

    @Autowired
    private lateinit var mPubgParticipantMapper: PubgParticipantMapper

    @Autowired
    private lateinit var mPubgMatchMapper: PubgMatchMapper

    @Transactional
    override fun insertPubgMatch(matchDetail: PubgMatchDetail) {
        mPubgMatchDetailMapper.insertPubgMatch(matchDetail)
        mPubgRosterMapper.insertPubgRosterList(
            ArrayList<PubgRoster>(matchDetail.rosterMap.values))
        mPubgParticipantMapper.insertPubgParticipantList(
            ArrayList(matchDetail.participantDetailMap.values))
    }

    @Transactional
    override fun deleteAllByMatchId(matchId: String) {
        mPubgMatchDetailMapper.deleteByMatchId(matchId)
        mPubgRosterMapper.delteByMatchId(matchId)
        mPubgParticipantMapper.deleteByMatchId(matchId)
    }

    override fun queryByMatchId(matchId: String): PubgMatchDetail? {
        val pubgMatchDetail = mPubgMatchDetailMapper.queryByMatchId(matchId) ?: return null
        pubgMatchDetail.participantDetailMap.putAll(mPubgParticipantMapper.queryMapByMatchId(matchId))
        pubgMatchDetail.rosterMap.putAll(mPubgRosterMapper.queryMapByMatchId(matchId))
        return pubgMatchDetail
    }
}

interface PubgPlayerRepoService {
    fun insertPubgPlayer(pubgPlayer: PubgPlayer)
    fun deleteAllByPlayerId(playerId: String)
    fun queryByPlayerName(shardId: String, playerName: String): PubgPlayer?
}

@Service
open class PubgPlayerRepoServiceImpl : PubgPlayerRepoService {

    @Autowired
    private lateinit var mPubgPlayerMapper: PubgPlayerMapper
    @Autowired
    private lateinit var mPubgMatchMapper: PubgMatchMapper
    @Autowired
    private lateinit var mPubgMatchDetailMapper: PubgMatchDetailMapper
    @Autowired
    private lateinit var mPubgRosterMapper: PubgRosterMapper
    @Autowired
    private lateinit var mPubgParticipantMapper: PubgParticipantMapper

    @Transactional
    override fun insertPubgPlayer(pubgPlayer: PubgPlayer) {
        mPubgPlayerMapper.insertPubgPlayer(pubgPlayer)
        mPubgMatchMapper.insertPubgMatchList(pubgPlayer.matches)
    }

    @Transactional
    override fun deleteAllByPlayerId(playerId: String) {
        val pubgMatches = mPubgMatchMapper.queryByPlayerId(playerId)
        if (pubgMatches.size == 0) {
            return
        }
        val matchIds = java.util.ArrayList<String>()
        pubgMatches.forEach { t -> matchIds.add(t.matchId) }
        mPubgPlayerMapper.deleteByPubgPlayerId(playerId)
        mPubgMatchMapper.deleteByPlayerId(playerId)
        mPubgMatchDetailMapper.deleteByMatchIdList(matchIds)
        mPubgRosterMapper.deleteByMatchIdList(matchIds)
        mPubgParticipantMapper.deleteByMatchIdList(matchIds)
    }

    override fun queryByPlayerName(shardId: String, playerName: String): PubgPlayer? {
        return mPubgPlayerMapper.queryByPlayerName(playerName)
    }
}