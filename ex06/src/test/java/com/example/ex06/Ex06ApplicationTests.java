package com.example.ex06;

import com.example.ex06.controller.MemberController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ex06ApplicationTests {

    @Autowired
    MemberController memberController;

    @BeforeAll
    static void beforeAll() {
        // DB 확인
        System.out.println("전체 테스트 시작");
    }

    @AfterAll
    static void afterAll() {
        // DB 테이블삭제
        System.out.println("전체 테스트 종료");
    }

    @BeforeEach
    void aaa() {
        System.out.println("테스트 시작");
    }

    @AfterEach
    void bbb() {
        System.out.println("테스트 종료");
    }

    @Test
    @DisplayName("컨텍스트 로드 테스트")
    void contextLoads() {
        System.out.println("잘되나");
    }

    @Test
    @DisplayName("테스트 메서드")
    void testMethod() {
        System.out.println("테스트 메서드");
    }

}
