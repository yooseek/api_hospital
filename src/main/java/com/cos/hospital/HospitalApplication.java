package com.cos.hospital;

import java.io.IOException;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@EnableScheduling
@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(HospitalApplication.class, args);
	}

	@Bean
	public SessionLocaleResolver localResolver(){
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.KOREA);
		return localeResolver;
	}
}
