package com.cos.hospital;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(HospitalApplication.class, args);
	}
	@Scheduled(cron= "0 * * * * *",zone = "Asia/Seoul")
 	public void startBatch() {
		System.out.println("cron Test 입니다.");
	}
}
