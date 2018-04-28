package xyz.ivyxjc.pubg4j.httpclient;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import xyz.ivyxjc.pubg4j.JsonBuilder;
import xyz.ivyxjc.pubg4j.constants.ApiConstants;
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.entity.PubgPlayer;
import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException;

/**
 * @author Ivyxjc
 * @since 4/23/2018
 */
@Service
@PropertySource(value = "classpath:private.yaml", ignoreResourceNotFound = false)
@Slf4j
public class GetApi {
    @Autowired
    private CloseableHttpClient httpClient;

    @Value("${pubg_authorization}")
    private String authorization;

    @Autowired
    private JsonBuilder mJsonBuilder;

    @Nullable
    public PubgPlayer filterPlayerName(String platformRegion, String playerName) {
        log.debug("start filterPlayerName...");
        String url = ApiConstants.FILTER_PLAYER_NAME;
        url = String.format(url, platformRegion, playerName);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/vnd.api+json");
        httpGet.setHeader("Authorization", authorization);
        HttpResponse response = null;
        PubgPlayer player = null;
        try {
            response = httpClient.execute(httpGet);
            if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            player = mJsonBuilder.buildPlayer(entity.getContent());
            log.info("player's id {}", player.getPlayerId());
        } catch (IOException | UnsupportedPubgElementException e) {
            log.error(e.getMessage());
        }
        return player;
    }

    @Nullable
    public PubgMatchDetail filterMatchId(String platformRegion, String matchId) {
        String url = ApiConstants.FILTER_MATCH_ID;
        url = String.format(url, platformRegion, matchId);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/vnd.api+json");
        httpGet.setHeader("Authorization", authorization);
        HttpResponse response = null;
        PubgMatchDetail pubgMatchDetail = null;
        try {
            response = httpClient.execute(httpGet);
            if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            pubgMatchDetail = mJsonBuilder.buildMatch(entity.getContent());
            log.info("match's id {}", pubgMatchDetail.getMatchId());
        } catch (IOException | UnsupportedPubgElementException e) {
            log.error(e.getMessage());
        }
        return pubgMatchDetail;
    }
}
