package eu.kielczewski.akanke.common.config;

import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class PegdownConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PegDownProcessor pegDownProcessor() {
        return new PegDownProcessor();
    }

}
