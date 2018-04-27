package xyz.ivyxjc.pubg4j.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ivyxjc.pubg4j.JsonBuilder;
import xyz.ivyxjc.pubg4j.Pubg4jApplication;
import xyz.ivyxjc.pubg4j.common.TestConstans;
import xyz.ivyxjc.pubg4j.dao.PubgMatchDetailMapper;
import xyz.ivyxjc.pubg4j.dao.PubgParticipantMapper;
import xyz.ivyxjc.pubg4j.dao.PubgRosterMapper;
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException;
import xyz.ivyxjc.pubg4j.service.PubgMatchService;
import xyz.ivyxjc.pubg4j.service.PubgPlayerService;

/**
 * @author Ivyxjc
 * @since 4/26/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Pubg4jApplication.class)
public class PubgMatchDetailMapperTest extends BaseMapperTest {

    @Autowired
    PubgMatchService mPubgMatchService;
    @Autowired
    private JsonBuilder mJsonBuilder;
    @Autowired
    private PubgMatchDetailMapper mPubgMatchDetailMapper;
    @Autowired
    private PubgRosterMapper mPubgRosterMapper;
    @Autowired
    private PubgParticipantMapper mPubgParticipantMapper;
    @Autowired
    private PubgPlayerService mPubgPlayerService;

    @Before
    @Override
    public void initData() throws UnsupportedPubgElementException {
        prepareMatchData();
    }

    @Test
    public void testInsertPubgMatchDetail() throws UnsupportedPubgElementException {
        prepareMatchData();
    }

    @Test
    public void testQueryByPlayerId() {
        PubgMatchDetail pubgMatchDetail =
            mPubgMatchDetailMapper.queryByMatchId(TestConstans.TEST_MATCH_ID);
        Assert.assertNotNull(pubgMatchDetail);
    }
}
