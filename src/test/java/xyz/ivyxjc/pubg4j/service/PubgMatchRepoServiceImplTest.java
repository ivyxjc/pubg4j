package xyz.ivyxjc.pubg4j.service;

import java.io.InputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource(value = "classpath:application.yaml")
public class PubgMatchRepoServiceImplTest {
    @Autowired
    private PubgMatchRepoServiceImpl mPubgMatchRepoServiceImpl;

    @Autowired
    private JsonBuilder mJsonBuilder;

    @Test
    public void insertPubgMatchTest() throws UnsupportedPubgElementException {
        mPubgMatchRepoServiceImpl.deleteAllByMatchId(TestConstans.TEST_MATCH_ID);
        InputStream in = getClass().getClassLoader().getResourceAsStream("match.json");
        PubgMatchDetail pubgMatch = mJsonBuilder.buildMatch(in);
        System.out.print("pubgPlayer: ");
        System.out.println(pubgMatch);
        System.out.println("+++++++++++++");
        System.out.println(pubgMatch.getRosterMap().size());
        mPubgMatchRepoServiceImpl.insertPubgMatch(pubgMatch);
    }
}
