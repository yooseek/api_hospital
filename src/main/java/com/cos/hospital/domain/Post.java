package com.cos.hospital.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    //User : Post - > 1:N
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JsonIgnore // 외부에 공개하지않을꺼다
    private User user;
}

