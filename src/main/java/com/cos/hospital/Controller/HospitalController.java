package com.cos.hospital.Controller;

import java.util.List;

import com.cos.hospital.domain.HospitalRepository;
import com.cos.hospital.domain.Hospital;

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

    @GetMapping("/api/sgguNm")
    // json으로 바꿀예정
    public @ResponseBody List<String> sgguNm(String sidoNm){
        return hRepository.mFindSgguNm(sidoNm);
    }

    @GetMapping("/api/hospital")
    // json으로 바꿀예정
    public @ResponseBody List<Hospital> hospitals(String sidoNm , String sgguNm){

        return hRepository.mFindHospital(sidoNm,sgguNm);
    }
}
