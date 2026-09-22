package com.example.ex06.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberControllerAdvice {

    // memberService에서 예외가 발생하게 되면 여기서 처리해서
    // 프론트에게 해당하는 id가 없어서 에러가 발생했다고 보내주게 됩니다.
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e){
        return e.getMessage();
    }

}
