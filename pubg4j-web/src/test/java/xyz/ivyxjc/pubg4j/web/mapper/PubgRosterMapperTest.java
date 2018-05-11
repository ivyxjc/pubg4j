package xyz.ivyxjc.pubg4j.web.mapper;

import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ivyxjc.pubg4j.web.dao.PubgRosterMapper;
import xyz.ivyxjc.pubg4j.web.entity.PubgRoster;
import xyz.ivyxjc.pubg4j.web.exception.UnsupportedPubgElementException;
import xyz.ivyxjc.pubg4j.web.types.PlatformRegion;

/**
 * @author Ivyxjc
 * @since 4/27/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubgRosterMapperTest extends BaseMapperTest {

    @Autowired
    private PubgRosterMapper mPubgRosterMapper;

    @Override
    @Before
    public void initData() throws UnsupportedPubgElementException {
        prepareMatchData();
    }

    @Test
    public void testInsertPubgMatchDetail() throws UnsupportedPubgElementException {
        prepareMatchData();
    }

    @Test
    public void testQueryByMatchId() {
        List<PubgRoster> list =
            mPubgRosterMapper.queryByMatchId("d0893a1b-70ea-4930-b67f-b0c970bd03e5");
        Assert.assertEquals(37, list.size());
    }

    @Test
    public void testQueryByRosterId() {
        PubgRoster pubgRoster =
            mPubgRosterMapper.queryByRosterId("4786c241-f832-408e-84b4-4f33c46fc111");
        Assert.assertNotNull(pubgRoster);
        Assert.assertEquals(PlatformRegion.PC_AS, pubgRoster.getShardId());
        Assert.assertEquals(new Integer(22), pubgRoster.getRank());
        Assert.assertEquals(new Integer(18), pubgRoster.getTeamId());
        Assert.assertEquals(false, pubgRoster.getWin());
    }

    @Test
    public void testQuer() {
        Map<String, PubgRoster> map =
            mPubgRosterMapper.queryMapByMatchId("d0893a1b-70ea-4930-b67f-b0c970bd03e5");
        Assert.assertNotNull(map);
    }
}
