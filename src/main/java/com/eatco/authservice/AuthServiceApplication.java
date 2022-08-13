package com.eatco.authservice;

import java.awt.image.BufferedImage;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;

import feign.Contract;

@SpringBootApplication
@EnableAsync
@EnableFeignClients
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}



	@Bean
	public Contract useFeignAnnotations() {
		return new Contract.Default();
	}

	@Bean
	public HttpMessageConverter<BufferedImage> httpMessageConverter() {
		return new BufferedImageHttpMessageConverter();
	}
}
