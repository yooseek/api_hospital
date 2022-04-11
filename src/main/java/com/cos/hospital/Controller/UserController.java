package com.cos.hospital.Controller;

import com.cos.hospital.Exception.UserNotFoundException;
import com.cos.hospital.domain.User;
import com.cos.hospital.domain.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    public Optional<User> retrieveUsers(@PathVariable Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
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
