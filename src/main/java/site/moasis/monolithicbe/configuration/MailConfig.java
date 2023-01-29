package site.moasis.monolithicbe.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailConfig{

    private final AppProperties prop;

    @Bean
    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(prop.getMail().getHost());
        javaMailSender.setUsername(prop.getMail().getUsername());
        javaMailSender.setPassword(prop.getMail().getPassword());
        javaMailSender.setPort(prop.getMail().getPort());
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {

        Properties properties = new Properties();

        properties.setProperty("mail.transport.protocol", prop.getMail().getProtocol());
        properties.setProperty("mail.smtp.auth", prop.getMail().getValue());
        properties.setProperty("mail.smtp.starttls.enable", prop.getMail().getValue());
        properties.setProperty("mail.debug", prop.getMail().getValue());
        properties.setProperty("mail.smtp.ssl.trust",prop.getMail().getHost());
        properties.setProperty("mail.smtp.ssl.enable",prop.getMail().getValue());

        return properties;
    }
}
