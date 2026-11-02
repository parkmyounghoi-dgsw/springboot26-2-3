package hs.kr.pmh.suhang.repository;

import hs.kr.pmh.suhang.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseQueryMethodRepository extends JpaRepository<Course, Long> {

    // 강의명으로 단건 조회
    Optional<Course> findByName(String name);

    // 카테고리로 전체 조회
    List<Course> findAllByCategory(String category);

    // 카테고리별 조회 + 이름 오름차순 정렬
    List<Course> findAllByCategoryOrderByName(String category);

    // 카테고리별 강의 개수
    long countByCategory(String category);

    // 강의명 또는 카테고리 조회 (OR)
    List<Course> findByNameOrCategory(String name, String category);

    // 카테고리 + 평점 미만 조회
    List<Course> findByCategoryAndRatingLessThan(String category, int rating);

    // 강의명이 특정 문자열로 시작하는 조회
    List<Course> findByNameStartingWith(String prefix);

    // 특정 카테고리 중 평점이 minRating 이상인 조회
    List<Course> findByCategoryAndRatingGreaterThanEqual(String category, int minRating);

    // 강의명 존재 여부 확인
    boolean existsByName(String name);
}
