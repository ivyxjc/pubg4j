package xyz.ivyxjc.pubg4j;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.entity.PubgPlayer;
import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException;

/**
 * @author Ivyxjc
 * @since 4/24/2018
 */
public class JsonBuilerTest {

    private JsonBuilder mJsonBuilder = new JsonBuilder();

    @Test
    public void testBuildPlayer() throws UnsupportedPubgElementException, IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("player.json");
        PubgPlayer pubgPlayer = mJsonBuilder.buildPlayer(in);
        Assert.assertNotNull(pubgPlayer.getPlayerId());
    }

    @Test
    public void testBuildMatch() throws UnsupportedPubgElementException, IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("match.json");
        PubgMatchDetail pubgMatchDetail = mJsonBuilder.buildMatch(in);
        Assert.assertNotNull(pubgMatchDetail.getMatchId());
    }
}

