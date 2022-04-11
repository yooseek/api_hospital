package com.cos.hospital.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    //GET
    ///hello-world (endPoint)
    // @RequestMapping (method = RequestMethod.GET, path="/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    //반환되는 값이 객체이면 json으로 자동 변환
    @GetMapping(path = "/hello-world-been")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    // PathVariable vs RequestParam
    @GetMapping(path = "/hello-world-been/path-variable/{name}")
    public HelloWorldBean helloWorldBeanName(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s",name));
    }


}
