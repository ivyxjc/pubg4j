package xyz.ivyxjc.pubg4j.web.service;

import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;
import xyz.ivyxjc.pubg4j.web.JsonBuilder;
import xyz.ivyxjc.pubg4j.web.entity.PubgPlayer;
import xyz.ivyxjc.pubg4j.web.exception.UnsupportedPubgElementException;
import xyz.ivyxjc.pubg4j.web.utils.JsonParser;

public class JsonParserTest {

    private JsonBuilder mJsonBuilder = new JsonBuilder();

    @Test
    public void testBuildPlayer() throws UnsupportedPubgElementException {
        InputStream in =
            getClass().getClassLoader().getResourceAsStream("json-samples/player.json");
        PubgPlayer oldParserPlayer = mJsonBuilder.buildPlayer(in);
        in = getClass().getClassLoader().getResourceAsStream("json-samples/player.json");
        PubgPlayer newPlayer = JsonParser.Companion.parserPlayer(in);
        Assert.assertEquals(oldParserPlayer.playerId, newPlayer.playerId);
        Assert.assertNotNull(newPlayer.getPlayerId());
    }
}
