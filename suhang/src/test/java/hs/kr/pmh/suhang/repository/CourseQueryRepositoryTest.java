package hs.kr.pmh.suhang.repository;

import hs.kr.pmh.suhang.domain.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
class CourseQueryRepositoryTest {

    @Autowired
    private CourseQueryRepository courseRepository;

    // ──────────────────────────────────────────────────────────────────────
    //  findByNameOrCategory: JPQL - 강의명 OR 카테고리 조회
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findByNameOrCategory_강의명이거나_카테고리인_강의를_반환한다() {
        // "Java Fundamentals"(Java 카테고리) + Python 카테고리 2건 = 총 3건
        List<Course> result = courseRepository.findByNameOrCategory("Java Fundamentals", "Python");

        assertThat(result).hasSize(3);
        assertThat(result)
                .extracting(Course::getName, Course::getCategory)
                .containsExactlyInAnyOrder(
                        tuple("Java Fundamentals",           "Java"),
                        tuple("Getting Started with Python", "Python"),
                        tuple("Game Development with Python","Python")
                );
    }

    // ──────────────────────────────────────────────────────────────────────
    //  findByCategoryAndMinRating: JPQL - 카테고리 + 최소 평점 이상 조회
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findByCategoryAndMinRating_Spring중_평점4이상인_강의를_반환한다() {
        List<Course> result = courseRepository.findByCategoryAndMinRating("Spring", 4);

        assertThat(result).isNotEmpty();
        assertThat(result)
                .allSatisfy(course -> {
                    assertThat(course.getCategory()).isEqualTo("Spring");
                    assertThat(course.getRating()).isGreaterThanOrEqualTo(4);
                });
    }

    @Sql("/insert-courses.sql")
    @Test
    void findByCategoryAndMinRating_존재하지_않는_조건이면_빈_리스트를_반환한다() {
        // Python 카테고리에 rating >= 10 인 강의는 없음
        List<Course> result = courseRepository.findByCategoryAndMinRating("Python", 10);

        assertThat(result).isEmpty();
    }

    // ──────────────────────────────────────────────────────────────────────
    //  findByCategoryOrderByRatingDesc: JPQL - 카테고리별 평점 내림차순 정렬
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findByCategoryOrderByRatingDesc_평점_내림차순으로_정렬된다() {
        List<Course> result = courseRepository.findByCategoryOrderByRatingDesc("Spring");

        assertThat(result).hasSize(3);
        // 첫 번째 강의 평점이 가장 높아야 함
        assertThat(result.get(0).getRating())
                .isGreaterThanOrEqualTo(result.get(result.size() - 1).getRating());
        // 전체 평점 순서가 내림차순인지 확인
        List<Integer> ratings = result.stream()
                .map(Course::getRating)
                .toList();
        for (int i = 0; i < ratings.size() - 1; i++) {
            assertThat(ratings.get(i)).isGreaterThanOrEqualTo(ratings.get(i + 1));
        }
    }

    // ──────────────────────────────────────────────────────────────────────
    //  findByCategoryNative: Native SQL - 카테고리로 조회
    //  JPQL과 동일한 결과를 반환하지만 실제 테이블명/컬럼명으로 작성됨
    // ──────────────────────────────────────────────────────────────────────

    @Sql("/insert-courses.sql")
    @Test
    void findByCategoryNative_Native_SQL로_카테고리_조회가_동작한다() {
        List<Course> result = courseRepository.findByCategoryNative("JavaScript");

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(Course::getCategory)
                .containsOnly("JavaScript");
    }

    @Sql("/insert-courses.sql")
    @Test
    void findByCategoryNative_JPQL과_동일한_결과를_반환한다() {
        String category = "Java";

        // JPQL 방식 (findByCategoryAndMinRating rating >= 1 = 전체)
        List<Course> jpqlResult = courseRepository.findByCategoryAndMinRating(category, 1);
        // Native SQL 방식
        List<Course> nativeResult = courseRepository.findByCategoryNative(category);

        assertThat(nativeResult).hasSize(jpqlResult.size());
        assertThat(nativeResult)
                .extracting(Course::getName)
                .containsExactlyInAnyOrderElementsOf(
                        jpqlResult.stream().map(Course::getName).toList()
                );
    }
}
