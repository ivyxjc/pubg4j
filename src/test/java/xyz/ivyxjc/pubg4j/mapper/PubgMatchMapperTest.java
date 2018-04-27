package xyz.ivyxjc.pubg4j.mapper;

import java.util.List;
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
import xyz.ivyxjc.pubg4j.dao.PubgMatchMapper;
import xyz.ivyxjc.pubg4j.dao.PubgPlayerMapper;
import xyz.ivyxjc.pubg4j.entity.PubgMatch;
import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException;
import xyz.ivyxjc.pubg4j.service.PubgPlayerService;

/**
 * @author Ivyxjc
 * @since 4/27/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Pubg4jApplication.class)
public class PubgMatchMapperTest extends BaseMapperTest {

    @Autowired
    private PubgPlayerService mPubgPlayerService;
    @Autowired
    private PubgMatchMapper mPubgMatchMapper;
    @Autowired
    private PubgPlayerMapper mPubgPlayerMapper;
    @Autowired
    private JsonBuilder mJsonBuilder;

    @Override
    @Before
    public void initData() throws UnsupportedPubgElementException {
        preparePlayerData();
    }

    @Test
    public void testQueryByPlayerId() {
        List<PubgMatch> list = mPubgMatchMapper.queryByPlayerId(TestConstans.TEST_PLAYER_ID);
        Assert.assertNotNull(list);
        Assert.assertEquals(250, list.size());
    }

    @Test
    public void testQueryByMatchId() {
        PubgMatch pubgMatch = mPubgMatchMapper.queryByMatchId(TestConstans.TEST_MATCH_ID);
        Assert.assertNotNull(pubgMatch);
    }
}
