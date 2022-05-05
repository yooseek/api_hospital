package com.cos.hospital.batch;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
class Response {
    private Header header;
    private Body body;
}

@Data
class Header {
    // 결과 코드
    private String resultCode;
    // 결과 메세지
    private String resultMsg;
}

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
class Item {
    // 운영 가능 일자
    private Integer adtFrDd;
    // 선정 유형
    private String hospTyTpCd;
    // 시군구명
    private String sgguNm;
    // 시도명
    private String sidoNm;
    // 구분코드
    private String spclAdmTyCd;
    // 전화번호
    private String telno;
    // 기관명
    private String yadmNm;
}

@Data
class Items {
    List<Item> item = null;
}

@Data
class Body {
    // 아이템 리스트
    private Items items;
    // 한 페이지 결과 수
    private Integer numOfRows;
    // 페이지 수
    private Integer pageNo;
    // 데이터 총 갯수
    private Integer totalCount;
}

@Data
public class ResponseDto {
    private Response response;
}