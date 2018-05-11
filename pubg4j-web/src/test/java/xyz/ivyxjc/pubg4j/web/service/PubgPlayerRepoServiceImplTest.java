package xyz.ivyxjc.pubg4j.web.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ivyxjc
 * @since 4/28/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource(value = "classpath:application.yaml")
public class PubgPlayerRepoServiceImplTest {
    @Autowired
    private PubgPlayerRepoServiceImpl mPubgPlayerRepoServiceImpl;

    @Test
    public void testDeleteAllByPlayerId() {
        mPubgPlayerRepoServiceImpl.deleteAllByPlayerId("account.39290179b73b44d5bdb41d17ea951655");
    }
}
