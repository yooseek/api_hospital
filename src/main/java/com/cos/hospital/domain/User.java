package com.cos.hospital.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonFilter("UserInfo") // 필터이름
//@ApiModel(description = "사용자 상세 정보")
@Schema(description = "유저")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer Id2;
    //@ApiModelProperty(notes = "사용자의 이름을 입력해 주세요") // swagger2
    @Schema(description = "유저이름" , example = "시기") // swagger3
    @Size(min=2, message = "Name은 두 글자 이상 입력해 주세요.")
    private String name;
    @Past
    private Date joinDate;

    private String password;
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

}
