package xyz.ivyxjc.pubg4j.web.messages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ivyxjc
 * @since 5/5/2018
 */

@Slf4j
@Service
public class MessagesProducer {

    //@Autowired
    //private KafkaTemplate<String, String> mKafkaTemplate;

    public void sendPlayer(String shardId, String playerName) {
        //log.info("Send player message to Kafka: {}", playerName);
        //mKafkaTemplate.send(KafkaConstans.TOPIC_PUBG_PLAYER, playerName);
    }

    public void sendMatch(String shardId, String matchId) {
        //log.info("Send match message to Kafka: {}", matchId);
        //mKafkaTemplate.send(KafkaConstans.TOPIC_PUBG_MATCH, matchId);
    }
}

