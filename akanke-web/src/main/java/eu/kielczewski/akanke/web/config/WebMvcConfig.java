package eu.kielczewski.akanke.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.*;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class WebMvcConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Autowired
    private Environment env;

    @Value("${akanke.documents.path}")
    private String applicationDocumentsPath;

    @Value("${akanke.resources.path}")
    private String applicationResourcesPath;

    @Value("${spring.resources.cache-period:31557000}")
    private Integer cachePeriod;

    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        boolean devMode = this.env.acceptsProfiles("dev");
        boolean useResourceCache = !devMode;
        Integer useCachePeriod = devMode ? 0 : cachePeriod;

        registry.addResourceHandler("/public/**")
                .addResourceLocations("file:" + applicationResourcesPath + "/")
                .setCachePeriod(useCachePeriod)
                .resourceChain(useResourceCache)
                .addResolver(new GzipResourceResolver())
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
                .addTransformer(new AppCacheManifestTransformer());

        registry.addResourceHandler("/images/**", "/wp-content/uploads/**")
                .addResourceLocations("file:" + applicationDocumentsPath + "/resources/")
                .setCachePeriod(useCachePeriod)
                .resourceChain(useResourceCache)
                .addResolver(new PathResourceResolver())
                .addTransformer(new AppCacheManifestTransformer());

    }

}
