package batch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import domain.Hospital;


@Component
public class DownloadBatchTest {

	@Test
	public void start() {
		//1. 공공데이터 다운로드
		
		String serviceKey = "cd%2FYh69sjlw4yDYGYjzwtIsDBvxvvULRl3U6rI%2Bofh%2F774Vbx%2FbRBh14DKzxJmSRQ9WKxEEZF5ME01PunggJgA%3D%3D";
		int pageNo = 1;
		int numOfRows = 1000;
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
			

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
