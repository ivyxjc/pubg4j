package xyz.ivyxjc.pubg4j;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import xyz.ivyxjc.pubg4j.httpclient.GetApi;

@SpringBootApplication
@Slf4j
public class Pubg4jApplication {

    @Autowired
    private GetApi mGetApi;

    public static void main(String[] args) {
        SpringApplication.run(Pubg4jApplication.class, args);
    }

    @Bean
    public CommandLineRunner doSome() {
        return t -> {
            System.out.println("::::::::::::::");
            mGetApi.filterPlayerName("pc-as", "Snaketc_mozz");
            mGetApi.filterMatchId("pc-as", "94cb39be-983b-491b-aadf-bc781876cda6");
        };
    }

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClients.createDefault();
    }
}
