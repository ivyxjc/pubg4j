package xyz.ivyxjc.pubg4j.httpclient;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import xyz.ivyxjc.pubg4j.JsonBuilder;
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

    public PubgPlayer filterPlayerName(String platformRegion, String playerName) {
        String url = "https://api.playbattlegrounds.com/shards/%s/players?filter[playerNames]=%s";
        url = String.format(url, platformRegion, playerName);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/vnd.api+json");
        httpGet.setHeader("Authorization", authorization);
        HttpResponse response = null;
        PubgPlayer player = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            player = mJsonBuilder.buildPlayer(entity.getContent());
            log.info("player's id {}", player.getPlayerId());
        } catch (IOException | UnsupportedPubgElementException e) {
            log.error(e.getMessage());
        }
        return player;
    }

    public PubgMatchDetail filterMatchId(String platformRegion, String matchId) {
        String url = "https://api.playbattlegrounds.com/shards/%s/matches/%s";
        url = String.format(url, platformRegion, matchId);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/vnd.api+json");
        httpGet.setHeader("Authorization", authorization);
        HttpResponse response = null;
        PubgMatchDetail pubgMatchDetail = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            pubgMatchDetail = mJsonBuilder.buildMatch(entity.getContent());
            log.info("match's id {}", pubgMatchDetail.getMatchId());
        } catch (IOException | UnsupportedPubgElementException e) {
            log.error(e.getMessage());
        }
        return pubgMatchDetail;
    }
}
