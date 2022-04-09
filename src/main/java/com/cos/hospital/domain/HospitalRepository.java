package com.cos.hospital.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HospitalRepository extends JpaRepository<Hospital,Integer>{
    
    @Query(value = "SELECT * FROM hospital WHERE sidoNm = :sidoNm AND sgguNm = :sgguNm", nativeQuery = true)
    public List<Hospital> mFindHospital(@Param("sidoNm")String sidoNm,@Param("sgguNm")String sgguNm);

    // 시도명 목록 가져오기
    @Query(value = "SELECT distinct sidoNm FROM hospital ORDER BY sidoNm", nativeQuery = true)
    public List<String> mFindSidoNm();
    
    // 군구명 목록 가져오기 - Ajax를 이용해서 가져올 예정..
    @Query(value = "SELECT distinct sgguNm FROM hospital WHERE sidoNm = :sidoNm ORDER BY sgguNm", nativeQuery = true)
    public List<String> mFindSgguNm(@Param("sidoNm") String sidoNm);
    
}
