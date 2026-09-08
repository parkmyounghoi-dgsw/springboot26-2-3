package com.example.ex05.hello;

import com.example.ex05.computer.Computer;
import com.example.ex05.computer.MacBook;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloController {
    /* DI 방법
        1. Autowired
        2. 생성자 주입 private final
    */
    @Autowired
    private Computer computer;

    private final MacBook macBook;
    public HelloController(MacBook macBook) {
        this.macBook = macBook;
    }

    @GetMapping("macbook")
    public String macBook() {
        System.out.println("macBook"+macBook);
        return "Hello MacBook!";
    }

    @GetMapping("computer")
    public String computer(){
        computer.turnOn();
        return "computer";
    }

    // 상품명과 상품의 갯수를 받는 api 를 get 방식으로 만들어보세요
    // api/product
    // @Operation, @Parameter @RequestParam @GetMapping 사용하세요
    @GetMapping("api/hello")
    @Operation(summary = "이름을 보내시면 인사합니다.",description = "설명하고 싶은거 적는거")
    public Map<String, Object> hello(
            @Parameter(description = "이름 보내시면 되는 파라메터",example = "홍길동")
            @RequestParam(defaultValue = "익명") String name
    ) {
        System.out.println("hello " + name);
        return Map.of("message", "Hello World!");
    }

    @PostMapping("api/data")
    public Map<String, Object> data(@RequestBody Map<String, Object> body) {
        System.out.println("data " + body);
        return Map.of("message", "api/data");
    }

}
