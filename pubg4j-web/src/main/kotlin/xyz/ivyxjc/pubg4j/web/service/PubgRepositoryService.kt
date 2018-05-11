package xyz.ivyxjc.pubg4j.web.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.ivyxjc.pubg4j.web.dao.*
import xyz.ivyxjc.pubg4j.web.entity.PubgMatch
import xyz.ivyxjc.pubg4j.web.entity.PubgMatchDetail
import xyz.ivyxjc.pubg4j.web.entity.PubgPlayer
import xyz.ivyxjc.pubg4j.web.entity.PubgRoster
import java.sql.SQLException

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
    fun insertPubgPlayer(pubgPlayer: PubgPlayer): Int
    fun deleteAllByPlayerId(playerId: String)
    fun queryByPlayerName(shardId: String, playerName: String): PubgPlayer?
    fun insertPubgPlayerMatchesSummar(matches: List<PubgMatch>): Int
}

@Service
open class PubgPlayerRepoServiceImpl : PubgPlayerRepoService {
    @JvmField
    val logger = LoggerFactory.getLogger(PubgPlayerRepoServiceImpl::class.java)

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
    override fun insertPubgPlayer(pubgPlayer: PubgPlayer): Int {
        mPubgPlayerMapper.insertPubgPlayer(pubgPlayer)
        mPubgMatchMapper.insertPubgMatchList(pubgPlayer.matches)
        return 1
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

    @Transactional
    override fun insertPubgPlayerMatchesSummar(matches: List<PubgMatch>): Int {
        try {
            return mPubgMatchMapper.insertPubgMatchList(matches)
        } catch (e: SQLException) {
            logger.warn("insert PUBG_PLAYER_MATCH throw SQLIntegrityConstraintViolationException")
        }
        return -1
    }
}