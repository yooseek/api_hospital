package com.cos.hospital.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonFilter("UserInfo") // 필터이름
@ApiModel(description = "사용자 상세 정보")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer Id2;
    @ApiModelProperty(notes = "사용자의 이름을 입력해 주세요")
    @Size(min=2, message = "Name은 두 글자 이상 입력해 주세요.")
    private String name;
    @Past
    private Date joinDate;

    private String password;
    private String ssn;
}
