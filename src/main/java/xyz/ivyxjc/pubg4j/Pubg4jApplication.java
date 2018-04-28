package xyz.ivyxjc.pubg4j;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import xyz.ivyxjc.pubg4j.httpclient.GetApi;
import xyz.ivyxjc.pubg4j.service.PubgPlayerService;

@Slf4j
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("xyz.ivyxjc.pubg4j.dao")
public class Pubg4jApplication {

    @Autowired
    private GetApi mGetApi;

    @Autowired
    private PubgPlayerService mPubgPlayerService;

    public static void main(String[] args) {
        SpringApplication.run(Pubg4jApplication.class, args);
    }

    //@Bean
    //public CommandLineRunner doSome() {
    //    return t -> {
    //        PubgPlayer player=mGetApi.filterPlayerName("pc-as", "Snaketc_mozz");
    //        mPubgPlayerService.insertPubgPlayer(player);
    //        mGetApi.filterMatchId("pc-as", "94cb39be-983b-491b-aadf-bc781876cda6");
    //    };
    //}

    @Bean
    public RequestConfig requestConfig() {
        return RequestConfig.custom()
            .setSocketTimeout(10000)
            .setConnectTimeout(10000)
            .setConnectionRequestTimeout(10000)
            .build();
    }

    @Bean
    public CloseableHttpClient httpClient(RequestConfig requestConfig) {
        return HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    }
}
