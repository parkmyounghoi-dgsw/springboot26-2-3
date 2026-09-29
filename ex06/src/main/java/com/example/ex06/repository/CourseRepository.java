package com.example.ex06.repository;

import com.example.ex06.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Member와 함께 fetch join (N+1 방지용)
    @Query("SELECT c FROM Course c JOIN FETCH c.member WHERE c.member.id = :memberId")
    List<Course> findByMemberIdWithMember(@Param("memberId") Long memberId);
}
