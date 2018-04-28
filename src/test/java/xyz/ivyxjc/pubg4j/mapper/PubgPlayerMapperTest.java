package xyz.ivyxjc.pubg4j.mapper;

import java.io.InputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ivyxjc.pubg4j.JsonBuilder;
import xyz.ivyxjc.pubg4j.common.TestConstans;
import xyz.ivyxjc.pubg4j.dao.PubgMatchMapper;
import xyz.ivyxjc.pubg4j.dao.PubgPlayerMapper;
import xyz.ivyxjc.pubg4j.entity.PubgPlayer;
import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException;

/**
 * @author Ivyxjc
 * @since 4/25/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubgPlayerMapperTest extends BaseMapperTest {

    @Autowired
    private PubgPlayerMapper mPubgPlayerMapper;
    @Autowired
    private PubgMatchMapper mPubgMatchMapper;
    @Autowired
    private JsonBuilder mJsonBuilder;

    @Override
    @Before
    public void initData() throws UnsupportedPubgElementException {
        preparePlayerData();
    }

    @Test
    public void testInsertPubgPlayer() throws UnsupportedPubgElementException {
        preparePlayerData();
    }

    @Test
    public void testQueryByPlayerId() {
        PubgPlayer pubgPlayer = mPubgPlayerMapper.queryByPlayerId(TestConstans.TEST_PLAYER_ID);
        Assert.assertNotNull(pubgPlayer);
        Assert.assertNotNull(pubgPlayer.getMatches());
        Assert.assertTrue(pubgPlayer.getMatches().size() > 1);
    }

    @Test
    public void testQueryByPlayerName() {
        PubgPlayer pubgPlayer = mPubgPlayerMapper.queryByPlayerName("Snaketc_mozz");
        Assert.assertNotNull(pubgPlayer);
        Assert.assertNotNull(pubgPlayer.getMatches());
        System.out.println("+++++++++++++++");
        System.out.println(pubgPlayer.getPlayerId());
        System.out.println(pubgPlayer.getMatches().size());
    }

    private void prepareData() throws UnsupportedPubgElementException {
        mPubgPlayerService.deleteAllByPlayerId(TestConstans.TEST_PLAYER_ID);
        InputStream in = getClass().getClassLoader().getResourceAsStream("player.json");
        PubgPlayer pubgPlayer = mJsonBuilder.buildPlayer(in);
        mPubgPlayerMapper.insertPubgPlayer(pubgPlayer);
        mPubgMatchMapper.insertPubgMatchList(pubgPlayer.getMatches());
    }
}
