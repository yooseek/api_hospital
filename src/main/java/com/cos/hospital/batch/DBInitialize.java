package com.cos.hospital.batch;

import com.cos.hospital.domain.Hospital;
import com.cos.hospital.domain.HospitalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DBInitialize {


    @Bean
    public CommandLineRunner initDB(HospitalRepository hospitalRepository) {
        return (args) -> {
            //1. 공공데이터 다운로드
            String serviceKey = "cd%2FYh69sjlw4yDYGYjzwtIsDBvxvvULRl3U6rI%2Bofh%2F774Vbx%2FbRBh14DKzxJmSRQ9WKxEEZF5ME01PunggJgA%3D%3D";
            int pageNo = 1;
            int numOfRows = 1000;
            // A0 - 국민안심병원 , 97 - 코로나검사실시기관, 99 - 코로나 선별진료소 운영기관
            String[] spclAdmTyCd = {"97", "99"}; // "A0" 에러
            String[] tempType = {"국민안심병원", "코로나 검사 실시기관", "코로나 선별진료소 운영기관"};
            String type = "json";

            try {
                // 가져온 데이터를 담을 콜렉션
                List<Hospital> hospitals = new ArrayList<>();

                // spclAdmTyCd 갯수 만큼 종류 만큼
                for (int i = 0; i < spclAdmTyCd.length; i++) {

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
                            .queryParam("spclAdmTyCd", spclAdmTyCd[i])
                            .queryParam("_type", type)
                            .build(true);

                    // get 요청으로 url보내서 ResponseDto로 맵핑
                    ResponseDto dto = rt.getForObject(uriComponents.toUri(), ResponseDto.class);
                    //System.out.println(dto.getResponse());
                    if (dto.getResponse().getBody().getItems().getItem().isEmpty()) {
                        continue;
                    }
                    List<Item> items = dto.getResponse().getBody().getItems().getItem();

                    // 카테고리로 바꿔서 DB에 저장
                    String category = tempType[i];
                    // 콜렉션에 담기
                    hospitals.addAll(items.stream().map(
                            (s) -> Hospital.builder()
                                    .adtFrDd(s.getAdtFrDd())
                                    .hospTyTpCd(s.getHospTyTpCd())
                                    .sgguNm(s.getSgguNm())
                                    .sidoNm(s.getSidoNm())
                                    .spclAdmTyCd(category)
                                    .telno(s.getTelno())
                                    .yadmNm(s.getYadmNm())
                                    .build()
                    ).collect(Collectors.toList()));
                }

                // 기존 데이터 다 삭제하기 - yml에서 ddl update로 체크하기
                hospitalRepository.deleteAll();

                // 배치시간에 DB에 insert하기 ( 하루에 한번 ?)
                hospitalRepository.saveAll(hospitals);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

    }

}
