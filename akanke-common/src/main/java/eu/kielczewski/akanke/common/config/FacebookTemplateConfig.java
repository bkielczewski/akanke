package eu.kielczewski.akanke.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

@Configuration
public class FacebookTemplateConfig {

    @Bean
    public FacebookTemplate facebookTemplate() {
        return new FacebookTemplate("");
    }

}
