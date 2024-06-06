package com.finalProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication

public class EnquiryAndCibilApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnquiryAndCibilApplication.class, args);
	}
	
	@Bean
	public RestTemplate Rt()
	{
		RestTemplate rt=new RestTemplate();
		return rt;
		
	}

}
