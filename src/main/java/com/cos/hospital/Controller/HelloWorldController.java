package com.cos.hospital.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

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

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized
            (@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        // 프로퍼티에 등록한 이름, 그것이 가변 변수라면 채울 파라메타, Locale 객체
        return messageSource.getMessage("greeting.message",null,locale);
    }

}
