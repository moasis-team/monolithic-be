package site.moasis.monolithicbe.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LocaleConfig {
    @Bean
    public ResourceBundleMessageSource messageSource() {

        ResourceBundleMessageSource source= new ResourceBundleMessageSource();
        source.setBasenames("messages/message");
        source.setUseCodeAsDefaultMessage(true);

        return source;
    }
}
