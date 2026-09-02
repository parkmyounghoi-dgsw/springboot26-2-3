package com.dgsw.org;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Tag(name = "01. 초간단 API", description = "DTO 없이 만드는 Controller")
@RestController
public class HelloController{

    // [GET] 파라미터 받아서 출력하기
    @Operation(summary = "인사하기 (GET)", description = "이름을 파라미터로 받아 인사 메시지를 반환합니다.")
    @GetMapping("/api/hello")
    public Map<String, Object> sayHello(
            @Parameter(description = "사용자 이름", example = "홍길동")
            @RequestParam(defaultValue = "익명") String name){

        Map<String, Object> result = new HashMap<>();
        result.put("message", name + "님, 안녕하세요!");
        result.put("status", 200);

        return result; // JSON 형태로 자동 변환되어 응답함
    }

    // [POST] Map으로 데이터 받아서 출력하기
    @Operation(summary = "데이터 보내기 (POST)", description = "JSON 데이터를 받아서 그대로 확인합니다.")
    @PostMapping("/api/data")
    public Map<String, Object> receiveData(@RequestBody Map<String, Object> body){

        Map<String, Object> result = new HashMap<>();
        result.put("receivedData", body);
        result.put("result", "성공적으로 수신되었습니다.");

        return result;
    }

    @Operation(summary = "짝수 필터링", description = "from ~ to 범위에서 짝수만 반환합니다.")
    @GetMapping("/stream")
    public Map<String, List<Integer>> stream(@RequestParam int from,
                                             @RequestParam int to) {

        List<Integer> evens = IntStream.rangeClosed(from, to) // from 이상 to 이하의 연속된 정수 스트림 생성 (양 끝 포함)
                .filter(i -> i % 2 == 0)                     // 람다식: 2로 나눈 나머지가 0인 수(짝수)만 통과
                .boxed()                                      // 기본형 int → 래퍼 클래스 Integer로 변환 (List에 담기 위해 필요)
                .toList();                                    // 스트림을 불변 List<Integer>로 최종 수집

        // 결과를 "evens" 키로 감싸서 JSON 객체 형태로 반환
        return Map.of("evens", evens);
    }

}