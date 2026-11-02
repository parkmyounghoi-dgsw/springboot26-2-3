package hs.kr.pmh.suhang.repository;

import hs.kr.pmh.suhang.domain.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
class CourseQueryMethodRepositoryTest {

    @Autowired
    private CourseQueryMethodRepository courseRepository;

    // ──────────────────────────────────────────────────────────────────────
    //  findByName: 강의명으로 단건 조회
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findByName_존재하는_강의명이면_Optional에_값이_있다() {
        Optional<Course> existing = courseRepository.findByName("Java Fundamentals");
        Optional<Course> missing  = courseRepository.findByName("Kotlin Basics");

        assertThat(existing).isPresent();
        assertThat(existing.get().getName()).isEqualTo("Java Fundamentals");
        assertThat(missing).isNotPresent();
    }

    // ──────────────────────────────────────────────────────────────────────
    //  findAllByCategory: 카테고리로 전체 조회
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findAllByCategory_Spring이면_3건이_반환된다() {
        List<Course> courses = courseRepository.findAllByCategory("Spring");

        assertThat(courses).hasSize(3);
        assertThat(courses)
                .extracting(Course::getCategory)
                .containsOnly("Spring");
    }

    // ──────────────────────────────────────────────────────────────────────
    //  findAllByCategoryOrderByName: 카테고리 조회 + 이름 오름차순 정렬
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findAllByCategoryOrderByName_이름_오름차순으로_정렬된다() {
        List<Course> courses = courseRepository.findAllByCategoryOrderByName("Spring");

        assertThat(courses).hasSize(3);
        assertThat(courses)
                .extracting(Course::getName)
                .isSorted();
    }

    // ──────────────────────────────────────────────────────────────────────
    //  countByCategory: 카테고리별 강의 개수
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void countByCategory_존재하는_카테고리는_개수를_반환하고_없으면_0이다() {
        assertThat(courseRepository.countByCategory("Spring")).isEqualTo(3);
        assertThat(courseRepository.countByCategory("Kotlin")).isZero();
    }

    // ──────────────────────────────────────────────────────────────────────
    //  findByNameOrCategory: 강의명 OR 카테고리 조회
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findByNameOrCategory_강의명이거나_카테고리인_강의를_반환한다() {
        // "Spring with Java"(Java 카테고리) + Python 카테고리 2건 = 총 3건
        List<Course> courses = courseRepository.findByNameOrCategory("Spring with Java", "Python");

        assertThat(courses).hasSize(3);
        assertThat(courses)
                .extracting(Course::getName, Course::getCategory)
                .containsExactlyInAnyOrder(
                        tuple("Spring with Java",            "Java"),
                        tuple("Getting Started with Python", "Python"),
                        tuple("Game Development with Python","Python")
                );
    }

    // ──────────────────────────────────────────────────────────────────────
    //  findByCategoryAndRatingLessThan: 카테고리 + 평점 미만
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findByCategoryAndRatingLessThan_Spring중_평점5미만인_강의를_반환한다() {
        List<Course> courses = courseRepository.findByCategoryAndRatingLessThan("Spring", 5);

        assertThat(courses).isNotEmpty();
        assertThat(courses)
                .extracting(Course::getCategory)
                .containsOnly("Spring");
        assertThat(courses)
                .extracting(Course::getRating)
                .allMatch(rating -> rating < 5);
    }

    // ──────────────────────────────────────────────────────────────────────
    //  findByNameStartingWith: 강의명이 특정 문자열로 시작하는 조회
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findByNameStartingWith_Java로_시작하는_강의를_반환한다() {
        List<Course> courses = courseRepository.findByNameStartingWith("Java");

        assertThat(courses).hasSize(2);
        assertThat(courses)
                .extracting(Course::getName)
                .allMatch(name -> name.startsWith("Java"));
    }

    // ──────────────────────────────────────────────────────────────────────
    //  findByCategoryAndRatingGreaterThanEqual: 카테고리 + 최소 평점 이상
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findByCategoryAndRatingGreaterThanEqual_Spring중_평점4이상인_강의를_반환한다() {
        List<Course> courses = courseRepository.findByCategoryAndRatingGreaterThanEqual("Spring", 4);

        assertThat(courses).hasSize(2); // rating 4, 5
        assertThat(courses)
                .extracting(Course::getRating)
                .allMatch(rating -> rating >= 4);
    }

    // ──────────────────────────────────────────────────────────────────────
    //  existsByName: 강의명 존재 여부
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void existsByName_존재하면_true_없으면_false를_반환한다() {
        assertThat(courseRepository.existsByName("Java Fundamentals")).isTrue();
        assertThat(courseRepository.existsByName("Kotlin Basics")).isFalse();
    }
}
