package xyz.ivyxjc.pubg4j.web.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.ivyxjc.pubg4j.web.entity.PubgMatch;
import xyz.ivyxjc.pubg4j.web.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.web.entity.PubgPlayer;
import xyz.ivyxjc.pubg4j.web.httpclient.GetApi;
import xyz.ivyxjc.pubg4j.web.messages.MessagesProducer;
import xyz.ivyxjc.pubg4j.web.service.PubgMatchCacheRepoService;
import xyz.ivyxjc.pubg4j.web.service.PubgMatchWebService;
import xyz.ivyxjc.pubg4j.web.service.PubgPlayerWebService;

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

    @GetMapping(value = {"/{shardId}/player/{name}"})
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

    @GetMapping(value = "/{shardId}/match/{id}")
    public PubgMatchDetail queryMatch(@PathVariable("shardId") String shardId,
        @PathVariable("id") String matchId) {
        return mPubgMatchWebService.doFilter(shardId, matchId);
    }
}
