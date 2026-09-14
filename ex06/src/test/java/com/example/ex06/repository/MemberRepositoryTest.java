package com.example.ex06.repository;

import com.example.ex06.entity.Course;
import com.example.ex06.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.Commit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EntityManager em; // 쓰기 지연 테스트를 위해 주입

    @Test
    void saveMembers() {
        System.out.println("--- save() 호출 시작 ---");
        memberRepository.save(new Member(null, "A", "a@test.com"));
        memberRepository.save(new Member(null, "B", "b@test.com"));
        System.out.println("--- save() 호출 완료 (이 시점에는 DB에 쿼리가 안 나감) ---");

        System.out.println("--- flush() 호출 시작 ---");
        em.flush(); // 쓰기 지연 큐에 쌓인 INSERT 쿼리를 DB로 강제 전송
        System.out.println("--- flush() 호출 완료 (이 시점에 INSERT 쿼리 출력됨) ---");
    }

    @Test
    @Transactional
    @Commit
    void updateMember() {
        Member member = memberRepository.findById(1L).get();
        member.changeName("김철수");
        memberRepository.save(member);
//        em.flush(); // 쓰기 지연 큐에 쌓인 UPDATE 쿼리를 DB로 강제 전송
    }

    @Test
    void 지연로딩() {
        Course course = courseRepository.findById(1L).get();
        System.out.println("Course 조회 완료");
        Member member = course.getMember();
        System.out.println("Member 조회 완료");
    }
}