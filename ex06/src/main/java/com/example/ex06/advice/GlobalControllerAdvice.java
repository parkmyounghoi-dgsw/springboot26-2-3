package com.example.ex06.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidException(
                                            MethodArgumentNotValidException e){
        Map<String,String> map = new HashMap<>();
        for (FieldError fi : e.getBindingResult().getFieldErrors()){
            map.put(fi.getField(), fi.getDefaultMessage());
        }
        return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(map);
    }

    // memberService에서 예외가 발생하게 되면 여기서 처리해서
    // 프론트에게 해당하는 id가 없어서 에러가 발생했다고 보내주게 됩니다.
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e){
        return e.getMessage();
    }

}
