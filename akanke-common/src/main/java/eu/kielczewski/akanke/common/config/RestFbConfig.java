package eu.kielczewski.akanke.common.config;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestFbConfig {

    @Bean
    public FacebookClient defaultFacebookClient() {
        return new DefaultFacebookClient();
    }

}
