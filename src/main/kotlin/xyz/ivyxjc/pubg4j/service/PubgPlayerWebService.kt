package xyz.ivyxjc.pubg4j.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.ivyxjc.pubg4j.entity.PubgPlayer
import xyz.ivyxjc.pubg4j.httpclient.GetApi

/**
 * @author Ivyxjc
 * @since 4/28/2018
 */
@Service
class PubgPlayerWebService {

    @Autowired
    private lateinit var mPugbPlayerService: PubgPlayerService

    @Autowired
    private lateinit var mGetApi: GetApi

    fun doFilter(shardId: String, playerName: String): PubgPlayer? {
        var pubgPlayer = mPugbPlayerService.queryByPlayerName(playerName)
        if (pubgPlayer == null) {
            pubgPlayer = mGetApi.filterPlayerName(shardId, playerName)
            if (pubgPlayer != null) {
                mPugbPlayerService.insertPubgPlayer(pubgPlayer)
            }
        }
        return pubgPlayer
    }
}