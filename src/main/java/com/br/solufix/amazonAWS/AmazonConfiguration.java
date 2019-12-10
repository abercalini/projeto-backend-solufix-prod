package com.br.solufix.amazonAWS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfiguration {
	
	
	private static final String ACCESS_KEY = "AKIAIOI4ATSQTCZ2LL6Q";
	private static final String SECRET_ACCESS_KEY = "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";
	
	
	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(ACCESS_KEY, SECRET_ACCESS_KEY);
	}
	
	@Bean
	public AmazonS3 amazonS3() {
		return AmazonS3ClientBuilder.standard().withRegion(Regions.SA_EAST_1)
					.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
	}
	
	
}
