package com.dgsw.org.lambda;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * IntStream 람다 예제 컨트롤러
 *
 * <p>JSON으로 범위(from, to)를 받아 IntStream을 생성하고,
 * 람다식(filter)으로 짝수만 골라 JSON 리스트로 응답합니다.</p>
 *
 * <p>Stream API의 핵심 연산 흐름:</p>
 * <pre>
 *   IntStream.rangeClosed(from, to)   // 1. 범위 스트림 생성
 *       .filter(i -> i % 2 == 0)      // 2. 중간 연산 - 짝수 필터링 (람다식)
 *       .boxed()                      // 3. int -> Integer 박싱
 *       .toList()                     // 4. 최종 연산 - List로 수집
 * </pre>
 */
@Tag(name = "Lambda", description = "IntStream 람다 API")
@RestController
@RequestMapping("/api/lambda")
public class StreamController {

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

    @Operation(summary = "학생 목록 정렬", description = "반 오름차순 → 총점 내림차순으로 정렬된 학생 목록을 반환합니다.")
    @GetMapping("/students")
    public List<Student> students() {

        // Stream.of()로 가상의 학생 데이터 스트림 생성
        return Stream.of(
                new Student("이자바", 3, 300),
                new Student("김자바", 1, 200),
                new Student("안자바", 2, 100),
                new Student("박자바", 2, 150),
                new Student("소자바", 1, 200),
                new Student("나자바", 3, 290),
                new Student("감자바", 3, 180)
        )
                // 1차 정렬: ban 기준 오름차순
                .sorted(Comparator.comparing(Student::getBan)
                        // 2차 정렬: ban이 같으면 Student.compareTo 적용 (totalScore 내림차순)
                        .thenComparing(Comparator.naturalOrder()))
                .toList(); // 최종 수집
    }

    static class Student implements Comparable<Student> {

        String name;      // 이름
        int ban;          // 반
        int totalScore;   // 총점

        // 생성자
        Student(String name, int ban, int totalScore) {
            this.name = name;
            this.ban = ban;
            this.totalScore = totalScore;
        }

        // Getter (Stream 메서드 참조용)
        public String getName()    { return name; }
        public int getBan()        { return ban; }
        public int getTotalScore() { return totalScore; }

        /**
         * totalScore 기준 내림차순 정렬
         * - 반환값 양수: this가 s보다 뒤에 위치
         * - 반환값 음수: this가 s보다 앞에 위치
         */
        @Override
        public int compareTo(Student s) {
            return s.totalScore - this.totalScore; // 타객체 점수 - 내 점수 → 내림차순
        }
    }
}
