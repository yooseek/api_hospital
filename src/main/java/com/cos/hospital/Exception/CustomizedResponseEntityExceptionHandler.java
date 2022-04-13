package com.cos.hospital.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice // 모든 컨트롤러가 실행될 때 이 부분이 실행 - AOP
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // Exception이 발생했을 때
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));

        // 500번 서버에러
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // UserNotFoundException이 발생했을 때
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));

        // 500번 서버에러
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override // - 부모인 ResponseEntityExceptionHandler에서 Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),"Validation Failed",ex.getBindingResult().toString());
        // Bad Request 에러
        return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
    }
}
