package eu.kielczewski.akanke.web.config;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@Configuration
public class TomcatConfig {

    @Bean
    public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
        return servletContainer -> ((TomcatEmbeddedServletContainerFactory) servletContainer).addConnectorCustomizers(
                connector -> {
                    AbstractHttp11Protocol httpProtocol = (AbstractHttp11Protocol) connector.getProtocolHandler();
                    httpProtocol.setCompression("on");
                    httpProtocol.setCompressionMinSize(256);
                    String mimeTypes = httpProtocol.getCompressableMimeTypes();
                    mimeTypes += "," + MediaType.APPLICATION_JSON_VALUE;
                    mimeTypes += "," + "text/css";
                    mimeTypes += "," + "application/javascript";
                    httpProtocol.setCompressableMimeTypes(mimeTypes);
                }
        );
    }

}
