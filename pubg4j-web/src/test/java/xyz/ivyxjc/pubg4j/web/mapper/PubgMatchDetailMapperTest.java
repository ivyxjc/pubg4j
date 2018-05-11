package xyz.ivyxjc.pubg4j.web.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ivyxjc.pubg4j.web.common.TestConstans;
import xyz.ivyxjc.pubg4j.web.dao.PubgMatchDetailMapper;
import xyz.ivyxjc.pubg4j.web.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.web.exception.UnsupportedPubgElementException;

/**
 * @author Ivyxjc
 * @since 4/26/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubgMatchDetailMapperTest extends BaseMapperTest {

    @Autowired
    private PubgMatchDetailMapper mPubgMatchDetailMapper;

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
