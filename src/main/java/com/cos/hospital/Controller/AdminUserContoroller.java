package com.cos.hospital.Controller;

import com.cos.hospital.Exception.UserNotFoundException;
import com.cos.hospital.domain.User;
import com.cos.hospital.domain.UserRepository;
import com.cos.hospital.domain.UserV2;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserContoroller {
    private final UserRepository userRepository;

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = userRepository.findAll();

        // filter 생성 - 지정한 4개 컬럼만 가져옴
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        // filterProvider 를 이용해서 필터 생성 - UserInfo이름에 만든 filter 추가
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // User를 Jackson을 이용해 MappingJacksonValue로 생성
        MappingJacksonValue mapping = new MappingJacksonValue(users);
        // 거기다가 필터 등록
        mapping.setFilters(filters);

        return mapping;
    }

    // 버전 1
//    @GetMapping("/v1/users/{id}") // URI 방식
//    @GetMapping(value = "/user/{id}/", params = "version=1") // Param 방식
//    @GetMapping(value = "/user/{id}", headers = "X-API-VERSION=1") // Header 방식
    @GetMapping(value = "/user/{id}", produces = "application/vnd.company.appv1+json") //MIME Type
    @Operation(description = "버전 1")
    public MappingJacksonValue retrieveUsersV1(@PathVariable Integer id) {
        // User를 찾음
        Optional<User> user = userRepository.findById(id);
        // 없으면 UserNotFoundException 발생
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // filter 생성 - 지정한 4개 컬럼만 가져옴
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        // filterProvider 를 이용해서 필터 생성 - UserInfo이름에 만든 filter 추가
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // User를 Jackson을 이용해 MappingJacksonValue로 생성
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        // 거기다가 필터 등록
        mapping.setFilters(filters);

        return mapping;
    }

    // 버전 2
//    @GetMapping("/v2/users/{id}") // URI 방식
//    @GetMapping(value = "/user/{id}/", params = "version=2") // Param 방식
//    @GetMapping(value = "/user/{id}", headers = "X-API-VERSION=2") // Header 방식
    @GetMapping(value = "/user/{id}", produces = "application/vnd.company.appv2+json") //MIME Type
    @Operation(description = "버전 2")
    public MappingJacksonValue retrieveUsersV2(@PathVariable Integer id) {
        // User를 찾음
        Optional<User> user = userRepository.findById(id);
        // 없으면 UserNotFoundException 발생
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        // filter 생성 - 지정한 4개 컬럼만 가져옴
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        // filterProvider 를 이용해서 필터 생성 - UserInfoV2이름에 만든 filter 추가
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        // User를 Jackson을 이용해 MappingJacksonValue로 생성
        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        // 거기다가 필터 등록
        mapping.setFilters(filters);

        return mapping;
    }
}
