package com.brilworks.accounts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    @Value("${spring.mail.host}")
    String mailHost;
    @Value("${spring.mail.port}")
    String mailPort;
    @Value("${spring.mail.username}")
    String mailUser;
    @Value("${spring.mail.smtp.auth.mechanisms}")
    String mailAuthMechanisms;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    String mailSmtpAuth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    String mailSmtpStarttlsEnable;
    @Value("${mail.smtp.debug}")
    String mailSmtpDebug;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(Integer.valueOf(mailPort));
        mailSender.setUsername(mailUser);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.debug", mailSmtpDebug);
        props.put("mail.smtp.auth.mechanisms", mailAuthMechanisms);

        return mailSender;
    }

}
