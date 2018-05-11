package xyz.ivyxjc.pubg4j.web.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.ivyxjc.pubg4j.web.dao.PubgMatchMapper
import xyz.ivyxjc.pubg4j.web.entity.PubgMatch
import xyz.ivyxjc.pubg4j.web.httpclient.GetApi

/**
 * @author Ivyxjc
 * @since 5/7/2018
 */


interface PubgRecevierHandlerService {
    fun refreshPlayerData(shardId: String, playerName: String): Int

}

@Service
class PubgRecevierHandlerServiceImpl : PubgRecevierHandlerService {
    private val log = LoggerFactory.getLogger(PubgPlayerWebServiceImpl::class.java)

    @Autowired
    private lateinit var mPugbPlayerRepoService: PubgPlayerRepoService

    @Autowired
    private lateinit var mPubgPlayerCacheRepoService: PubgPlayerCacheRepoService

    @Autowired
    private lateinit var mPubgMatchMapper: PubgMatchMapper

    @Autowired
    private lateinit var mGetApi: GetApi

    override fun refreshPlayerData(shardId: String, playerName: String): Int {
        if (!mPubgPlayerCacheRepoService.exist(shardId, playerName)) {
            val player = mGetApi.filterPlayerName(shardId, playerName)
            log.info("pubgPlayer detail:{}", player?.toString())
            if (player != null) {
                return mPugbPlayerRepoService.insertPubgPlayer(player)
            }
            return -1
        } else {
            val pubgPlayer = mGetApi.filterPlayerName(shardId, playerName)
            if (pubgPlayer == null) {
                return -1
            }
            val matches = pubgPlayer.matches
            val newestPubgMatch: PubgMatch? = mPubgMatchMapper.queryNewestMatch(shardId, pubgPlayer.playerId)
            log.info("shardId: {}", shardId)
            log.info("shardId: {}", playerName)
            log.info("newestPubgMatch: {}", newestPubgMatch)
            if (newestPubgMatch == null) {
                return mPugbPlayerRepoService.insertPubgPlayerMatchesSummar(matches)
            }
            val newList = mutableListOf<PubgMatch>()
            for (pm in matches) {
                if (newestPubgMatch.matchId.equals(pm.matchId)) {
                    break
                }
                newList.add(pm)
            }
            return mPugbPlayerRepoService.insertPubgPlayerMatchesSummar(newList)
        }
    }

}


