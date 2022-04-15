package com.cos.hospital.Controller;

import com.cos.hospital.Exception.UserNotFoundException;
import com.cos.hospital.domain.Post;
import com.cos.hospital.domain.PostRepository;
import com.cos.hospital.domain.User;
import com.cos.hospital.domain.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// swagger3
@Tag(name = "UserController")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //swgger3
    @Operation(description = "전체회원조회")
    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){

        // filter 생성 - 지정한 4개 컬럼만 가져옴
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","id2","name","joinDate","ssn","posts");
        // filterProvider 를 이용해서 필터 생성 - UserInfo이름에 만든 filter 추가
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        // User를 Jackson을 이용해 MappingJacksonValue로 생성
        MappingJacksonValue mapping = new MappingJacksonValue(userRepository.findAll());
        // 거기다가 필터 등록
        mapping.setFilters(filters);

        return mapping;
    }

    @Operation(description = "회원조회")
    @GetMapping("/users/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    })
    public MappingJacksonValue retrieveUser(@PathVariable Integer id){
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
    @Operation(description = "회원생성")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User newUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        // 201번 create 상태코드
        return ResponseEntity.created(location).build();
    }
    @Operation(description = "회원삭제")
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

    @Operation(description = "Post 조회")
    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveALLPostByUser(@PathVariable Integer id){

        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] 는 없는 사용자입니다.",id));
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    @Operation(description = "Post 생성")
    @Transactional
    public ResponseEntity createPost(@PathVariable Integer id, @RequestBody Post post){

        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] 는 없는 사용자입니다.",id));
        }

        post.setUser(user.get());
        Post newPost = postRepository.save(post);
        user.get().getPosts().add(newPost);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
