package xyz.ivyxjc.pubg4j.mapper;

import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.ivyxjc.pubg4j.JsonBuilder;
import xyz.ivyxjc.pubg4j.common.TestConstans;
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.entity.PubgPlayer;
import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException;
import xyz.ivyxjc.pubg4j.service.PubgMatchService;
import xyz.ivyxjc.pubg4j.service.PubgPlayerService;

/**
 * @author Ivyxjc
 * @since 4/28/2018
 */
@PropertySource(value = "classpath:application.properties")
public abstract class BaseMapperTest {
    @Autowired
    private PubgPlayerService mPubgPlayerService;
    @Autowired
    private PubgMatchService mPubgMatchService;
    @Autowired
    private JsonBuilder mJsonBuilder;
    @Autowired
    private JdbcTemplate mJdbcTemplate;

    void preparePlayerData() throws UnsupportedPubgElementException {
        mPubgPlayerService.deleteAllByPlayerId(TestConstans.TEST_PLAYER_ID);
        InputStream in = getClass().getClassLoader().getResourceAsStream("player.json");
        PubgPlayer pubgPlayer = mJsonBuilder.buildPlayer(in);
        mPubgPlayerService.insertPubgPlayer(pubgPlayer);
    }

    void prepareMatchData() throws UnsupportedPubgElementException {
        mPubgMatchService.deleteAllByMatchId(TestConstans.TEST_MATCH_ID);
        InputStream in = getClass().getClassLoader().getResourceAsStream("match.json");
        PubgMatchDetail pubgMatch = mJsonBuilder.buildMatch(in);
        mPubgMatchService.insertPubgMatch(pubgMatch);
    }

    abstract public void initData() throws UnsupportedPubgElementException;
}
