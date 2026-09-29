package com.example.ex06.repository;

import com.example.ex06.entity.Course;
import com.example.ex06.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(
                Member.builder()
                        .name("홍길동")
                        .email("hong@example.com")
                        .build()
        );

        courseRepository.save(Course.builder().name("Java 기초").member(savedMember).build());
        courseRepository.save(Course.builder().name("Spring Boot").member(savedMember).build());
    }

    @Test
    @DisplayName("Member 저장 및 단건 조회")
    void saveMember() {
        Member found = memberRepository.findById(savedMember.getId()).orElseThrow();

        assertThat(found.getName()).isEqualTo("홍길동");
        assertThat(found.getEmail()).isEqualTo("hong@example.com");
    }

    @Test
    @DisplayName("Member 전체 조회")
    void findAllMembers() {
        memberRepository.save(Member.builder().name("김철수").email("kim@example.com").build());

        List<Member> members = memberRepository.findAll();

        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("Course 지연 로딩 - member 접근 시 실제 객체 반환")
    void lazyLoadingTest() {
        // Course를 조회할 때 Member는 프록시 상태
        Course course = courseRepository.findAll().get(0);

        // getMember().getName() 호출 시점에 실제 SELECT 쿼리 발생 (지연 로딩)
        String memberName = course.getMember().getName();

        assertThat(memberName).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("Fetch Join으로 Course와 Member 한 번에 조회 (N+1 방지)")
    void fetchJoinTest() {
        List<Course> courses = courseRepository.findByMemberIdWithMember(savedMember.getId());

        assertThat(courses).hasSize(2);
        // fetch join으로 이미 초기화됐으므로 추가 쿼리 없음
        courses.forEach(c -> assertThat(c.getMember().getName()).isEqualTo("홍길동"));
    }

    @Test
    @DisplayName("Member 수정 (PUT - 전체 교체)")
    void updateMember() {
        savedMember.update("김길동", "kim@example.com");
        memberRepository.flush();

        Member updated = memberRepository.findById(savedMember.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo("김길동");
        assertThat(updated.getEmail()).isEqualTo("kim@example.com");
    }

    @Test
    @DisplayName("Member 수정 (PATCH - 이름만 변경)")
    void patchMember() {
        savedMember.patch("박길동", null);
        memberRepository.flush();

        Member patched = memberRepository.findById(savedMember.getId()).orElseThrow();
        assertThat(patched.getName()).isEqualTo("박길동");
        assertThat(patched.getEmail()).isEqualTo("hong@example.com"); // email 유지
    }

    @Test
    @DisplayName("Member 삭제")
    void deleteMember() {
        memberRepository.delete(savedMember);

        assertThat(memberRepository.findById(savedMember.getId())).isEmpty();
    }
}
