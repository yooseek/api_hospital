package com.cos.hospital.Controller;

import com.cos.hospital.Exception.UserNotFoundException;
import com.cos.hospital.domain.User;
import com.cos.hospital.domain.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUsers(@PathVariable Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        //HATEOAS 사용
        // EntityModel 클래스를 선언한다. - new 없이 of builder로 생성가능;
        EntityModel<Optional<User>> resource = EntityModel.of(user);
        // 연결을 시켜줄 수 있는 빌더 - retrieveAllUsers란 메서드를 가져와서 집어넣고
        WebMvcLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        // EntityModel에 retrieveAllUsers란 메서드가 들어간 빌더와 "all-users"라는 문자를 연결
        resource.add(linkBuilder.withRel("all-users"));

        // filter 생성 - 지정한 4개 컬럼만 가져옴
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");
        // filterProvider 를 이용해서 필터 생성 - UserInfo이름에 만든 filter 추가
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        // User를 Jackson을 이용해 MappingJacksonValue로 생성
        MappingJacksonValue mapping = new MappingJacksonValue(resource);
        // 거기다가 필터 등록
        mapping.setFilters(filters);

        return mapping;
    }

    // Valid 어노테이션 - 디펜던시 추가
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User newUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        // 201번 create 상태코드
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){

        Optional<User> findUser = userRepository.findById(id);
        if(findUser.isEmpty()){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }else{
            userRepository.deleteById(id);
        }

        return ResponseEntity.ok().build();
    }



}
