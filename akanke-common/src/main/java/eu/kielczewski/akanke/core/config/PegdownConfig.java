package eu.kielczewski.akanke.core.config;

import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PegdownConfig {

    @Bean
    public FactoryBean<PegDownProcessor> pegDownProcessorFactoryBean() {
        return new FactoryBean<PegDownProcessor>() {
            @Override
            public PegDownProcessor getObject() {
                return new PegDownProcessor();
            }

            @Override
            public Class<PegDownProcessor> getObjectType() {
                return PegDownProcessor.class;
            }

            @Override
            public boolean isSingleton() {
                return false;
            }
        };
    }

}
