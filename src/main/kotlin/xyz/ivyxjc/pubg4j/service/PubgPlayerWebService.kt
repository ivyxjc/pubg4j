package xyz.ivyxjc.pubg4j.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail
import xyz.ivyxjc.pubg4j.entity.PubgPlayer
import xyz.ivyxjc.pubg4j.httpclient.GetApi

/**
 * @author Ivyxjc
 * @since 4/28/2018
 */

interface PubgPlayerWebService {
    fun doFilter(shardId: String, playerName: String): PubgPlayer?
    fun insertIfNotExsits(shardId: String, playerName: String)
}

@Service
class PubgPlayerWebServiceImpl : PubgPlayerWebService {

    private val log = LoggerFactory.getLogger(PubgPlayerWebServiceImpl::class.java)

    @Autowired
    private lateinit var mPugbPlayerRepoService: PubgPlayerRepoService

    @Autowired
    private lateinit var mPubgPlayerCacheRepoService: PubgPlayerCacheRepoService

    @Autowired
    private lateinit var mGetApi: GetApi

    override fun doFilter(shardId: String, playerName: String): PubgPlayer? {
        return mPugbPlayerRepoService.queryByPlayerName(shardId, playerName)
    }

    override fun insertIfNotExsits(shardId: String, playerName: String) {
        if (!mPubgPlayerCacheRepoService.exist(shardId, playerName)) {
            val pubgPlayer = mGetApi.filterPlayerName(shardId, playerName)
            log.info("pubgPlayer detail:{}", pubgPlayer?.toString())
            Thread.sleep(2000)
            if (pubgPlayer != null) {
                mPugbPlayerRepoService.insertPubgPlayer(pubgPlayer)
            }
        }
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
    private lateinit var mGetApi: GetApi

    override fun doFilter(shardId: String, matchId: String): PubgMatchDetail? {
        return mPubgMatchRepoService.queryByMatchId(matchId)
    }

    override fun insertIfNotExsits(shardId: String, matchId: String) {
        val pubgMatchDetail = mGetApi.filterMatchId(shardId, matchId)
        Thread.sleep(2000)
        if (pubgMatchDetail != null) {
            mPubgMatchRepoService.insertPubgMatch(pubgMatchDetail)
        }
    }
}