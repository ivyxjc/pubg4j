package xyz.ivyxjc.pubg4j;

import com.alibaba.druid.support.http.StatViewServlet;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import xyz.ivyxjc.pubg4j.httpclient.GetApi;
import xyz.ivyxjc.pubg4j.service.PubgPlayerService;

@Slf4j
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("xyz.ivyxjc.pubg4j.dao")
@PropertySource(value = {"classpath:private.yaml", "classpath:application.yaml"},
                ignoreResourceNotFound = false)
public class Pubg4jApplication {

    @Autowired
    private GetApi mGetApi;

    @Autowired
    private PubgPlayerService mPubgPlayerService;

    @Value("${druid_user}")
    private String druidUsername;

    @Value("${druid_password}")
    private String druidPassword;


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

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("resetEnable", "false"); //禁用HTML页面上的“Rest All”功能
        initParameters.put("allow", "");  //ip白名单（没有配置或者为空，则允许所有访问）
        initParameters.put("deny", ""); //ip黑名单
        initParameters.put("loginUsername", druidUsername);  //++监控页面登录用户名
        initParameters.put("loginPassword", druidPassword);  //++监控页面登录用户密码
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

}
