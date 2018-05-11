package xyz.ivyxjc.pubg4j.web;

import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;
import xyz.ivyxjc.pubg4j.web.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.web.entity.PubgPlayer;
import xyz.ivyxjc.pubg4j.web.exception.UnsupportedPubgElementException;

/**
 * @author Ivyxjc
 * @since 4/24/2018
 */
public class JsonBuilerTest {

    private JsonBuilder mJsonBuilder = new JsonBuilder();

    @Test
    public void testBuildPlayer() throws UnsupportedPubgElementException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("player.json");
        PubgPlayer pubgPlayer = mJsonBuilder.buildPlayer(in);
        Assert.assertNotNull(pubgPlayer.getPlayerId());
    }

    @Test
    public void testBuildMatch() throws UnsupportedPubgElementException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("match.json");
        PubgMatchDetail pubgMatchDetail = mJsonBuilder.buildMatch(in);
        Assert.assertNotNull(pubgMatchDetail.getMatchId());
    }
}

