package xyz.ivyxjc.pubg4j.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xyz.ivyxjc.pubg4j.utils.KafkaConstans;

/**
 * @author Ivyxjc
 * @since 5/5/2018
 */
@Service
public class MessagesProducer {

    @Autowired
    private KafkaTemplate<String, String> mKafkaTemplate;

    public void sendPlayer(String shardId, String playerName) {
        mKafkaTemplate.send(KafkaConstans.TOPIC_PUBG_PLAYER, playerName);
    }

    public void sendMatch(String shardId, String matchId) {
        mKafkaTemplate.send(KafkaConstans.TOPIC_PUBG_MATCH, matchId);
    }
}
