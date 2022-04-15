package com.cos.hospital.Controller;

import java.util.List;

import com.cos.hospital.domain.HospitalRepository;
import com.cos.hospital.domain.Hospital;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class HospitalController {   

    private final HospitalRepository hRepository;

    @GetMapping("/")
    public String home() {
        return "index"; //templates/home.mustache 를 찾음
    }

    @GetMapping("/hospital")
    public String hospital(Model model) {
        model.addAttribute("sidoNms",hRepository.mFindSidoNm());
        return "hospital"; 
    }

    @Operation(description = "구 이름 조회")
    @GetMapping("/api/sgguNm")
    // json으로 바꿀예정
    public @ResponseBody List<String> sgguNm(String sidoNm){
        if(hRepository.mFindSgguNm(sidoNm).isEmpty()){
            throw new RuntimeException("잘못된 시도명입니다.");
        }
        return hRepository.mFindSgguNm(sidoNm);
    }

    @GetMapping("/api/hospital")
    @Operation(description = "병원 조회")
    // json으로 바꿀예정
    public @ResponseBody List<Hospital> hospitals(String sidoNm , String sgguNm){

        return hRepository.mFindHospital(sidoNm,sgguNm);
    }
}
