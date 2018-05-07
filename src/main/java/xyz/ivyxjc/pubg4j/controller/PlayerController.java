package xyz.ivyxjc.pubg4j.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.ivyxjc.pubg4j.entity.PubgMatch;
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.entity.PubgPlayer;
import xyz.ivyxjc.pubg4j.httpclient.GetApi;
import xyz.ivyxjc.pubg4j.messages.MessagesProducer;
import xyz.ivyxjc.pubg4j.service.PubgMatchCacheRepoService;
import xyz.ivyxjc.pubg4j.service.PubgMatchWebService;
import xyz.ivyxjc.pubg4j.service.PubgPlayerWebService;

/**
 * @author Ivyxjc
 * @since 5/5/2018
 */
@Api
@RestController
public class PlayerController {
    @Autowired
    private GetApi mGetApi;

    @Autowired
    private PubgPlayerWebService mPubgPlayerWebService;

    @Autowired
    private PubgMatchWebService mPubgMatchWebService;

    @Autowired
    private MessagesProducer mMessagesProducer;

    @Autowired
    private PubgMatchCacheRepoService mPubgMatchCacheRepoService;

    @RequestMapping(value = {"/{shardId}/player/{name}"}, method = RequestMethod.GET)
    public PubgPlayer queryPlayer(@PathVariable("shardId") String shardId,
        @PathVariable("name") String name) {
        mMessagesProducer.sendPlayer(shardId, name);
        PubgPlayer pubgPlayer = mPubgPlayerWebService.doFilter(shardId, name);
        Runnable thread = () -> {
            for (PubgMatch match : pubgPlayer.getMatches()) {
                if (!mPubgMatchCacheRepoService.exist(match.getMatchId())) {
                    mMessagesProducer.sendMatch(shardId, match.getMatchId());
                } else {
                    break;
                }
            }
        };
        if (pubgPlayer != null) {
            thread.run();
        }
        return pubgPlayer;
    }

    @RequestMapping(value = "/{shardId}/match/{id}", method = RequestMethod.GET)
    public PubgMatchDetail queryMatch(@PathVariable("shardId") String shardId,
        @PathVariable("id") String matchId) {
        return mPubgMatchWebService.doFilter(shardId, matchId);
    }
}
