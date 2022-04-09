package com.cos.hospital.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cos.hospital.domain.Hospital;
import com.cos.hospital.domain.HospitalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// 1주일에 한번씩 다운로드 해서 DB에 변경하기
// 전체 데이터를 가져오고 삭제 후 추가
@Slf4j
@Component
@RequiredArgsConstructor
public class DownloadBatch {
	
	private final HospitalRepository hospitalRepository;
	
	@Scheduled(cron= "0 * * * * *")
 	public void startBatch() throws Exception {
		log.info("cron test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("cron Test 입니다. !!!");
	}

	
	@Autowired
	public void Adowload () {
		//1. 공공데이터 다운로드
		
		String serviceKey = "cd%2FYh69sjlw4yDYGYjzwtIsDBvxvvULRl3U6rI%2Bofh%2F774Vbx%2FbRBh14DKzxJmSRQ9WKxEEZF5ME01PunggJgA%3D%3D";
		int pageNo = 1;
		int numOfRows = 1000;
		// A0 - 국민안심병원 , 97 - 코로나검사실시기관, 99 - 코로나 선별진료소 운영기관
		String spclAdmTyCd = "A0";
		String type = "json";
		try {
			// Dto 변환을 위한 RestTemplate
			RestTemplate rt = new RestTemplate();
			// URI를 쉽게 만들어주는 components
			UriComponents uriComponents = UriComponentsBuilder.newInstance()
		            .scheme("http")
		            .host("apis.data.go.kr")
		            .path("/B551182/pubReliefHospService/getpubReliefHospList")
		            .queryParam("serviceKey", serviceKey)
		            .queryParam("pageNo", pageNo)
		            .queryParam("numOfRows", numOfRows)
		            .queryParam("spclAdmTyCd", spclAdmTyCd)
		            .queryParam("_type", type)
		            .build(true);
			// get 요청으로 url보내서 ResponseDto로 맵핑
			ResponseDto dto = rt.getForObject(uriComponents.toUri(), ResponseDto.class);
			List<Item> items = dto.getResponse().getBody().getItems().getItem();

			// 가져온 데이터를 담을 콜렉션
			List<Hospital> hospitals = new ArrayList<>();
			hospitals = items.stream().map(
				(s) -> {
					return Hospital.builder()
					.adtFrDd(s.getAdtFrDd())
					.hospTyTpCd(s.getHospTyTpCd())
					.sgguNm(s.getSgguNm())
					.sidoNm(s.getSidoNm())
					.spclAdmTyCd(s.getSpclAdmTyCd())
					.telno(s.getTelno())
					.yadmNm(s.getYadmNm())
					.build();
				}
			).collect(Collectors.toList());

			// 배치시간에 DB에 insert하기 ( 하루에 한번 ?)
			hospitalRepository.saveAll(hospitals);
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			
		}
	}
	public void Bdowload () {
		//1. 공공데이터 다운로드
		
		String serviceKey = "cd%2FYh69sjlw4yDYGYjzwtIsDBvxvvULRl3U6rI%2Bofh%2F774Vbx%2FbRBh14DKzxJmSRQ9WKxEEZF5ME01PunggJgA%3D%3D";
		int pageNo = 1;
		int numOfRows = 1000;
		// A0 - 국민안심병원 , 97 - 코로나검사실시기관, 99 - 코로나 선별진료소 운영기관
		String spclAdmTyCd = "97";
		String type = "json";
		try {
			// Dto 변환을 위한 RestTemplate
			RestTemplate rt = new RestTemplate();
			// URI를 쉽게 만들어주는 components
			UriComponents uriComponents = UriComponentsBuilder.newInstance()
		            .scheme("http")
		            .host("apis.data.go.kr")
		            .path("/B551182/pubReliefHospService/getpubReliefHospList")
		            .queryParam("serviceKey", serviceKey)
		            .queryParam("pageNo", pageNo)
		            .queryParam("numOfRows", numOfRows)
		            .queryParam("spclAdmTyCd", spclAdmTyCd)
		            .queryParam("_type", type)
		            .build(true);
			// get 요청으로 url보내서 ResponseDto로 맵핑
			ResponseDto dto = rt.getForObject(uriComponents.toUri(), ResponseDto.class);
			
			List<Item> items = dto.getResponse().getBody().getItems().getItem();
			int count =0;
	        for(Item i : items) {
	        	System.out.println(i.getYadmNm()+" count: "+count++);
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void Cdowload () {
		//1. 공공데이터 다운로드
		
		String serviceKey = "cd%2FYh69sjlw4yDYGYjzwtIsDBvxvvULRl3U6rI%2Bofh%2F774Vbx%2FbRBh14DKzxJmSRQ9WKxEEZF5ME01PunggJgA%3D%3D";
		int pageNo = 1;
		int numOfRows = 1000;
		// A0 - 국민안심병원 , 97 - 코로나검사실시기관, 99 - 코로나 선별진료소 운영기관
		String spclAdmTyCd = "99";
		String type = "json";
		try {
			// Dto 변환을 위한 RestTemplate
			RestTemplate rt = new RestTemplate();
			// URI를 쉽게 만들어주는 components
			UriComponents uriComponents = UriComponentsBuilder.newInstance()
		            .scheme("http")
		            .host("apis.data.go.kr")
		            .path("/B551182/pubReliefHospService/getpubReliefHospList")
		            .queryParam("serviceKey", serviceKey)
		            .queryParam("pageNo", pageNo)
		            .queryParam("numOfRows", numOfRows)
		            .queryParam("spclAdmTyCd", spclAdmTyCd)
		            .queryParam("_type", type)
		            .build(true);
			// get 요청으로 url보내서 ResponseDto로 맵핑
			ResponseDto dto = rt.getForObject(uriComponents.toUri(), ResponseDto.class);
			
			List<Item> items = dto.getResponse().getBody().getItems().getItem();
			int count =0;
	        for(Item i : items) {
	        	System.out.println(i.getYadmNm()+" count: "+count++);
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
