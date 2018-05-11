package xyz.ivyxjc.pubg4j.web.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.ivyxjc.pubg4j.web.dao.PubgMatchMapper
import xyz.ivyxjc.pubg4j.web.entity.PubgMatchDetail
import xyz.ivyxjc.pubg4j.web.entity.PubgPlayer
import xyz.ivyxjc.pubg4j.web.httpclient.GetApi

/**
 * @author Ivyxjc
 * @since 4/28/2018
 */

interface PubgPlayerWebService {
    fun doFilter(shardId: String, playerName: String): PubgPlayer?
    fun insertIfNotExsits(shardId: String, playerName: String, refresh: Boolean): Int
}

@Service
open class PubgPlayerWebServiceImpl : PubgPlayerWebService {

    private val log = LoggerFactory.getLogger(PubgPlayerWebServiceImpl::class.java)

    @Autowired
    private lateinit var mPugbPlayerRepoService: PubgPlayerRepoService

    @Autowired
    private lateinit var mPubgPlayerCacheRepoService: PubgPlayerCacheRepoService

    @Autowired
    private lateinit var mPubgMatchMapper: PubgMatchMapper

    @Autowired
    private lateinit var mGetApi: GetApi

    override fun doFilter(shardId: String, playerName: String): PubgPlayer? {
        return mPugbPlayerRepoService.queryByPlayerName(shardId, playerName)
    }

    @Transactional
    override fun insertIfNotExsits(shardId: String, playerName: String, refresh: Boolean): Int {

        if (!mPubgPlayerCacheRepoService.exist(shardId, playerName)) {
            val pubgPlayer = mGetApi.filterPlayerName(shardId, playerName)
            log.info("pubgPlayer detail:{}", pubgPlayer?.toString())
            if (pubgPlayer != null) {
                return mPugbPlayerRepoService.insertPubgPlayer(pubgPlayer)
            }
            return -1
        }
        return -1
    }
}

interface PubgMatchWebService {
    fun doFilter(shardId: String, matchId: String): PubgMatchDetail?
    fun insertIfNotExsits(shardId: String, matchId: String)
}

@Service
class PubgMatchWebServiceImpl : PubgMatchWebService {

    @Autowired
    private lateinit var mPubgMatchRepoService: PubgMatchRepoService

    @Autowired
    private lateinit var mPubgMatchCacheRepoService: PubgMatchCacheRepoService

    @Autowired
    private lateinit var mGetApi: GetApi

    override fun doFilter(shardId: String, matchId: String): PubgMatchDetail? {
        return mPubgMatchRepoService.queryByMatchId(matchId)
    }

    override fun insertIfNotExsits(shardId: String, matchId: String) {
        if (mPubgMatchCacheRepoService.exist(matchId)) {
            return
        }
        val pubgMatchDetail = mGetApi.filterMatchId(shardId, matchId)
        Thread.sleep(2000)
        if (pubgMatchDetail != null) {
            mPubgMatchRepoService.insertPubgMatch(pubgMatchDetail)
        }
    }
}