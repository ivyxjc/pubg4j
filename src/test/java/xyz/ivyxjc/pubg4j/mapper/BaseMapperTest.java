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
import xyz.ivyxjc.pubg4j.service.PubgMatchRepoServiceImpl;
import xyz.ivyxjc.pubg4j.service.PubgPlayerRepoServiceImpl;

/**
 * @author Ivyxjc
 * @since 4/28/2018
 */
@PropertySource(value = "classpath:application.yaml")
public abstract class BaseMapperTest {
    @Autowired
    protected PubgPlayerRepoServiceImpl mPubgPlayerRepoServiceImpl;
    @Autowired
    protected PubgMatchRepoServiceImpl mPubgMatchRepoServiceImpl;
    @Autowired
    protected JsonBuilder mJsonBuilder;
    @Autowired
    protected JdbcTemplate mJdbcTemplate;

    void preparePlayerData() throws UnsupportedPubgElementException {
        mPubgPlayerRepoServiceImpl.deleteAllByPlayerId(TestConstans.TEST_PLAYER_ID);
        InputStream in = getClass().getClassLoader().getResourceAsStream("player.json");
        PubgPlayer pubgPlayer = mJsonBuilder.buildPlayer(in);
        mPubgPlayerRepoServiceImpl.insertPubgPlayer(pubgPlayer);
    }

    void prepareMatchData() throws UnsupportedPubgElementException {
        mPubgMatchRepoServiceImpl.deleteAllByMatchId(TestConstans.TEST_MATCH_ID);
        InputStream in = getClass().getClassLoader().getResourceAsStream("match.json");
        PubgMatchDetail pubgMatch = mJsonBuilder.buildMatch(in);
        mPubgMatchRepoServiceImpl.insertPubgMatch(pubgMatch);
    }

    abstract public void initData() throws UnsupportedPubgElementException;
}
