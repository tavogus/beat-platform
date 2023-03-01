package com.beatdepot.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.beatdepot.models.Config;
import com.beatdepot.services.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class AwsConfig {
    @Autowired
    private ConfigService configService;

    @Bean
    public AmazonS3Client S3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                configService.findValueByKey(Config.AWS_ACCESS_KEY_ID),
                configService.findValueByKey(Config.AWS_SECRET_ACCESS_KEY)
        );

        return (AmazonS3Client) AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(Regions.US_EAST_1).build();
    }
}
