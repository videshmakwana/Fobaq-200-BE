package com.brilworks.accounts.services.impl;

import com.brilworks.accounts.dto.EmailData;
import com.brilworks.accounts.dto.ForgetPassEmailData;
import com.brilworks.accounts.utils.AppProperties;
import com.brilworks.accounts.utils.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

@Service
public class MyEmailService {
    @Autowired(required = true)
    private JavaMailSender javaMailSender;
    @Autowired
    private AppProperties appProperties;


    private long tokenExpires = 0L;

    public void sendMail(EmailData myDto) {
//        setJavaMailSenderPassword();
        String message = "To activate your account, please click the link below \r\n" +
                appProperties.getDomainName() + "/org/" + myDto.getOrgId() + "/invite-form/" + myDto.getEmpId();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(Constants.COMPANY_MAIL);
        msg.setTo(myDto.getEmailTo());
        msg.setSubject("You are invited to CRM!");
        msg.setText(message);
        javaMailSender.send(msg);
    }

    public void genaRateForgetPasswordLink(ForgetPassEmailData forgetPassEmailData) {
        String message = "To reset your account, please click the link below \r\n" +
                appProperties.getDomainName() + "/reset/" + forgetPassEmailData.getToken();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("Reset Password Link..");
        msg.setFrom(Constants.COMPANY_MAIL);
        msg.setTo(forgetPassEmailData.getEmail());
        msg.setText(message);
        javaMailSender.send(msg);
    }

//    public void setJavaMailSenderPassword() {
//        String accessToken = "";
//        if (System.currentTimeMillis() > tokenExpires) {
//            try {
//                String request = "client_id=" + URLEncoder.encode(mailSmtpAuthClientId, "UTF-8")
//                        + "&client_secret=" + URLEncoder.encode(mailSmtpAuthClientSecret, "UTF-8")
//                        + "&refresh_token=" + URLEncoder.encode(smtpOauthRefreshToken, "UTF-8")
//                        + "&grant_type=refresh_token";
//                HttpURLConnection conn = (HttpURLConnection) new URL(mailSmtpAuthTokenUrl).openConnection();
//                conn.setDoOutput(true);
//                conn.setRequestMethod("POST");
//                PrintWriter out = new PrintWriter(conn.getOutputStream());
//                out.print(request);
//                out.flush();
//                out.close();
//                conn.connect();
//                try {
//                    HashMap<String, Object> result;
//                    result = new ObjectMapper().readValue(conn.getInputStream(), new TypeReference<>() {
//                    });
//                    accessToken = (String) result.get("access_token");
//                    tokenExpires = System.currentTimeMillis() + (((Number) result.get("expires_in")).intValue() * 1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            ((JavaMailSenderImpl)this.javaMailSender).setPassword(accessToken);
//        }
//
//
//    }
}