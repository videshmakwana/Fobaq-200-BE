package com.brilworks.accounts.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.brilworks.accounts.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials =
                new BasicAWSCredentials(Constants.ACCESS_KEY, Constants.SECRET_KEY);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Constants.AWS_REGION)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }
}
