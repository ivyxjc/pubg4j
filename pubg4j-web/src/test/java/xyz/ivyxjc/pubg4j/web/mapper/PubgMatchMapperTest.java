package xyz.ivyxjc.pubg4j.web.mapper;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ivyxjc.pubg4j.web.common.TestConstans;
import xyz.ivyxjc.pubg4j.web.dao.PubgMatchMapper;
import xyz.ivyxjc.pubg4j.web.entity.PubgMatch;
import xyz.ivyxjc.pubg4j.web.exception.UnsupportedPubgElementException;

/**
 * @author Ivyxjc
 * @since 4/27/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubgMatchMapperTest extends BaseMapperTest {

    @Autowired
    private PubgMatchMapper mPubgMatchMapper;

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

    @Test
    public void testQueryNewest() {
        PubgMatch pubgMatch =
            mPubgMatchMapper.queryNewestMatch("pc-as", TestConstans.TEST_PLAYER_ID);
        Assert.assertNotNull(pubgMatch);
    }

    @Test
    public void testInsertPubgMatchList() {
        List<PubgMatch> list = mPubgMatchMapper.queryByPlayerId(TestConstans.TEST_PLAYER_ID);
        System.out.println("++++++++++++++");
        System.out.println(list.size());
        mPubgPlayerRepoServiceImpl.deleteAllByPlayerId(TestConstans.TEST_PLAYER_ID);
        mPubgMatchMapper.insertPubgMatchList(list);
    }
}
