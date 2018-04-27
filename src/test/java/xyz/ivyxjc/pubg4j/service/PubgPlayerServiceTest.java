package xyz.ivyxjc.pubg4j.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ivyxjc
 * @since 4/28/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubgPlayerServiceTest {
    @Autowired
    private PubgPlayerService mPubgPlayerService;

    @Test
    public void testDeleteAllByPlayerId() {
        mPubgPlayerService.deleteAllByPlayerId("account.39290179b73b44d5bdb41d17ea951655");
    }
}
