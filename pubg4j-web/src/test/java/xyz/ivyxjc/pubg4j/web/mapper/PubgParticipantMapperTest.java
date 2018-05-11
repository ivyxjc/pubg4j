//package xyz.ivyxjc.pubg4j.mapper;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import xyz.ivyxjc.pubg4j.JsonBuilder;
//import xyz.ivyxjc.pubg4j.common.TestConstans;
//import xyz.ivyxjc.pubg4j.dao.PubgParticipantMapper;
//import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail;
//import xyz.ivyxjc.pubg4j.entity.PubgParticipant;
//import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException;
//
///**
// * @author Ivyxjc
// * @since 4/27/2018
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class PubgParticipantMapperTest extends BaseMapperTest {
//
//    @Autowired
//    private JsonBuilder mJsonBuilder;
//
//    @Autowired
//    private PubgParticipantMapper mPubgParticipantMapper;
//
//    @Override
//    @Before
//    public void initData() throws UnsupportedPubgElementException {
//        prepareMatchData();
//    }
//
//    @Test
//    public void prepareData() throws UnsupportedPubgElementException {
//        prepareMatchData();
//    }
//
//    @Test
//    public void testInsertPubgMatchDetail() throws UnsupportedPubgElementException {
//        mPubgParticipantMapper.deleteByMatchId(TestConstans.TEST_MATCH_ID);
//        InputStream in = getClass().getClassLoader().getResourceAsStream("match.json");
//        PubgMatchDetail pubgMatch = mJsonBuilder.buildMatch(in);
//        mPubgParticipantMapper.insertPubgParticipantList(
//            new ArrayList<>(pubgMatch.getParticipantDetailMap().values()));
//    }
//
//    @Test
//    public void testQueryByMatchId() {
//        List<PubgParticipant> list =
//            mPubgParticipantMapper.queryByMatchId(TestConstans.TEST_MATCH_ID);
//        Assert.assertEquals(86, list.size());
//    }
//
//    @Test
//    public void testQueryByParticipantId() {
//        PubgParticipant pubgParticipant =
//            mPubgParticipantMapper.queryByParticipantId("1a6fc797-73fc-4bbb-9261-45b3e51a9065");
//        Assert.assertNotNull(pubgParticipant);
//        Assert.assertEquals("d0893a1b-70ea-4930-b67f-b0c970bd03e5", pubgParticipant.getMatchId());
//        Assert.assertEquals("8760505b-7213-4797-8253-e14151594f00", pubgParticipant.getRosterId());
//        Assert.assertEquals("1a6fc797-73fc-4bbb-9261-45b3e51a9065",
//            pubgParticipant.getParticipantId());
//        Assert.assertEquals(new Integer(3), pubgParticipant.getDbnos());
//        Assert.assertEquals(new Integer(4), pubgParticipant.getHeals());
//        Assert.assertEquals(new Double(647.181400000000), pubgParticipant.getDamageDealt());
//        Assert.assertEquals(new Integer(3), pubgParticipant.getHeadshotKills());
//        Assert.assertEquals(new Integer(3), pubgParticipant.getKillPlace());
//        Assert.assertEquals(new Integer(989), pubgParticipant.getKillPoints());
//        Assert.assertEquals(new Double(52.931453700000), pubgParticipant.getKillPointsDelta());
//        Assert.assertEquals(new Integer(0), pubgParticipant.getKillStreaks());
//        Assert.assertEquals(new Integer(7), pubgParticipant.getKills());
//        Assert.assertEquals(new Integer(0), pubgParticipant.getLastKillPoints());
//        Assert.assertEquals(new Integer(0), pubgParticipant.getLastWinPoints());
//        Assert.assertEquals(new Double(44), pubgParticipant.getLongestKill());
//        Assert.assertEquals("K-YinXiang", pubgParticipant.getName());
//        Assert.assertEquals("account.4bb71bdda0e94355ab1cb8f88e35ea8d",
//            pubgParticipant.getPlayerId());
//    }
//
//    @Test
//    public void testQueryByRosterId() {
//        List<PubgParticipant> list =
//            mPubgParticipantMapper.queryByRosterId("8760505b-7213-4797-8253-e14151594f00");
//        Assert.assertEquals(4, list.size());
//    }
//
//    @Test
//    public void testQueryMapByMatchId() {
//        Map<String, PubgParticipant> map =
//            mPubgParticipantMapper.queryMapByMatchId("d0893a1b-70ea-4930-b67f-b0c970bd03e5");
//        Assert.assertNotNull(map);
//    }
//}
