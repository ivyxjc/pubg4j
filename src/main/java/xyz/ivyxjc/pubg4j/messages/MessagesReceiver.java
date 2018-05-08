package xyz.ivyxjc.pubg4j.messages;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import xyz.ivyxjc.pubg4j.service.PubgMatchWebService;
import xyz.ivyxjc.pubg4j.service.PubgPlayerWebService;
import xyz.ivyxjc.pubg4j.service.PubgRecevierHandlerService;
import xyz.ivyxjc.pubg4j.utils.KafkaConstans;

/**
 * @author Ivyxjc
 * @since 5/5/2018
 */
@Slf4j
@Service
public class MessagesReceiver {

    @Autowired
    private PubgPlayerWebService mPubgPlayerWebService;

    @Autowired
    private PubgRecevierHandlerService mPubgRecevierHandlerService;

    @Autowired
    private PubgMatchWebService mPubgMatchWebService;

    @KafkaListener(topics = {KafkaConstans.TOPIC_PUBG_PLAYER})
    public void listenPlayer(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String playerName = (String) kafkaMessage.get();
            log.info("--------receive-player-->: " + playerName);
            //mPubgPlayerWebService.insertIfNotExsits("pc-as", playerName,true);
            mPubgRecevierHandlerService.refreshPlayerData("pc-as", playerName);
        }
    }

    @KafkaListener(topics = {KafkaConstans.TOPIC_PUBG_MATCH})
    public void listenMatch(ConsumerRecord<?, String> record) {
        Optional<String> kafkaMesage = Optional.ofNullable(record.value());
        if (kafkaMesage.isPresent()) {
            String matchId = kafkaMesage.get();
            log.info("--------receive-match-->: " + matchId);
            mPubgMatchWebService.insertIfNotExsits("pc-as", matchId);
        }
    }
}
