package xyz.ivyxjc.pubg4j.service;

import java.io.InputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ivyxjc.pubg4j.JsonBuilder;
import xyz.ivyxjc.pubg4j.common.TestConstans;
import xyz.ivyxjc.pubg4j.entity.PubgMatchDetail;
import xyz.ivyxjc.pubg4j.exception.UnsupportedPubgElementException;

/**
 * @author Ivyxjc
 * @since 4/27/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubgMatchServiceTest {
    @Autowired
    private PubgMatchService mPubgMatchService;

    @Autowired
    private JsonBuilder mJsonBuilder;

    @Test
    public void insertPubgMatchTest() throws UnsupportedPubgElementException {
        mPubgMatchService.deleteAllByMatchId(TestConstans.TEST_MATCH_ID);
        InputStream in = getClass().getClassLoader().getResourceAsStream("match.json");
        PubgMatchDetail pubgMatch = mJsonBuilder.buildMatch(in);
        System.out.print("pubgPlayer: ");
        System.out.println(pubgMatch);
        System.out.println("+++++++++++++");
        System.out.println(pubgMatch.getRosterMap().size());
        mPubgMatchService.insertPubgMatch(pubgMatch);
    }
}
