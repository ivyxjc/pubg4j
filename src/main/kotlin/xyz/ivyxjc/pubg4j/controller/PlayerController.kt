package xyz.ivyxjc.pubg4j.controller

import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail
import xyz.ivyxjc.pubg4j.entity.PubgPlayer
import xyz.ivyxjc.pubg4j.httpclient.GetApi
import xyz.ivyxjc.pubg4j.service.PubgPlayerWebService

/**
 * @author Ivyxjc
 * @since 4/28/2018
 */

@Api
@RestController
class PlayerController {

    @Autowired
    private lateinit var mGetApi: GetApi

    @Autowired
    private lateinit var mPubgPlayerWebService: PubgPlayerWebService

    @RequestMapping(value = arrayOf("/{shardId}/player/{name}"), method = arrayOf(RequestMethod.GET))
    fun queryPlayer(@PathVariable("shardId") shardId: String, @PathVariable("name") name: String): PubgPlayer? {
        return mPubgPlayerWebService.doFilter(shardId, name)
    }

    @RequestMapping(value = arrayOf("/{shardId}/match/{id}"), method = arrayOf(RequestMethod.GET))
    fun queryMatch(@PathVariable("shardId") shardId: String, @PathVariable("id") id: String): PubgMatchDetail? {
        return mGetApi.filterMatchId(shardId, id)
    }
}