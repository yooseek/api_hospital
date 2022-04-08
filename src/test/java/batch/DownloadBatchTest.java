package batch;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class DownloadBatchTest {

	@Test
	public void start() {
		//1. 공공데이터 다운로드
		
		String serviceKey = "cd%2FYh69sjlw4yDYGYjzwtIsDBvxvvULRl3U6rI%2Bofh%2F774Vbx%2FbRBh14DKzxJmSRQ9WKxEEZF5ME01PunggJgA%3D%3D";
		int pageNo = 1;
		int numOfRows = 10;
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
	        for(Item i : items) {
	        	System.out.println(i.getYadmNm());
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
