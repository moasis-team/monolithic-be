package site.moasis.monolithicbe.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Mail mail = new Mail();
    private final Jwt jwt = new Jwt();

    @Data
    public static class Mail{
        private String protocol;
        private String value;
        private String host;
        private String username;
        private String password;
        private Integer port;
    }

    @Data
    public static class Jwt{
        private String header;
        private String access_token_secret;
        private Long access_token_validity_in_seconds;
        private String refresh_token_secret;
        private Long refresh_token_validity_in_seconds;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source= new ResourceBundleMessageSource();
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}