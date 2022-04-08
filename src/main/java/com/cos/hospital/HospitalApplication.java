package com.cos.hospital;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class HospitalApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(HospitalApplication.class, args);
	}

}
