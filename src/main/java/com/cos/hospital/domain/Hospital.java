package com.cos.hospital.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
public class Hospital {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // auto_increment

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
