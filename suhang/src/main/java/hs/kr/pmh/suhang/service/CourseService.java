package hs.kr.pmh.suhang.service;

import hs.kr.pmh.suhang.domain.Course;

import java.util.List;

public interface CourseService {

    // POST: 강의 등록
    Course create(Course course);

    // GET: 전체 조회
    List<Course> findAll();

    // GET: 단건 조회
    Course findById(Long id);

    // PUT: 전체 수정
    Course update(Long id, Course course);

    // PATCH: 부분 수정
    Course patch(Long id, Course course);

    // DELETE: 삭제
    boolean delete(Long id);
}
