package com.cos.hospital.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//lombok 사용
@Data  // setter + getter + toString
@AllArgsConstructor // 모든 생성자를 생성한다.
@NoArgsConstructor // 디폴트 생성자를 생성한다.
public class HelloWorldBean {
    private String massage;
}
