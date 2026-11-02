package hs.kr.pmh.suhang.repository;

import hs.kr.pmh.suhang.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseQueryRepository extends JpaRepository<Course, Long> {

    // 강의명 또는 카테고리로 조회 (JPQL)
    @Query("""
            SELECT c
            FROM Course c
            WHERE c.name = :name
               OR c.category = :category
            """)
    List<Course> findByNameOrCategory(
            @Param("name") String name,
            @Param("category") String category);

    // 카테고리 + 최소 평점 이상 조회 (JPQL)
    @Query("""
            SELECT c
            FROM Course c
            WHERE c.category = :category
              AND c.rating >= :minRating
            """)
    List<Course> findByCategoryAndMinRating(
            @Param("category") String category,
            @Param("minRating") int minRating);

    // 카테고리별 조회 + 평점 내림차순 정렬 (JPQL)
    @Query("""
            SELECT c
            FROM Course c
            WHERE c.category = :category
            ORDER BY c.rating DESC
            """)
    List<Course> findByCategoryOrderByRatingDesc(
            @Param("category") String category);

    // 카테고리로 조회 (Native SQL)
    // nativeQuery = true 로 실제 테이블명/컬럼명 사용
    @Query(
            value = "SELECT * FROM course WHERE category = :category",
            nativeQuery = true
    )
    List<Course> findByCategoryNative(
            @Param("category") String category);
}
