package xyz.ivyxjc.pubg4j.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.ivyxjc.pubg4j.dao.PubgMatchDetailMapper
import xyz.ivyxjc.pubg4j.dao.PubgPlayerMapper

/**
 * @author Ivyxjc
 * @since 5/7/2018
 */


interface PubgMatchCacheRepoService {
    fun exist(matchId: String): Boolean
}

@Service
class PubgMatchCacheRepoServiceImpl : PubgMatchCacheRepoService {
    @Autowired
    private lateinit var pubgMatchDetailMapper: PubgMatchDetailMapper

    override fun exist(matchId: String): Boolean {
        return pubgMatchDetailMapper.queryByMatchId(matchId) != null
    }
}

interface PubgPlayerCacheRepoService {
    fun exist(shardId: String, playerName: String): Boolean
}

@Service
class PubgPlayerCacheRepoServiceImpl : PubgPlayerCacheRepoService {
    @Autowired
    private lateinit var pubgPlayerMapper: PubgPlayerMapper

    override fun exist(shardId: String, playerName: String): Boolean {
        return pubgPlayerMapper.queryByPlayerName(playerName) != null
    }
}
