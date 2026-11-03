package hs.kr.pmh.suhang.service;

import hs.kr.pmh.suhang.domain.Course;
import hs.kr.pmh.suhang.repository.CourseQueryMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseQueryMethodRepository courseRepository;

    // POST: 강의 등록 (이름, 카테고리, 설명은 필수)
    @Override
    public Course create(Course course) {
        if (!StringUtils.hasText(course.getName())
                || !StringUtils.hasText(course.getCategory())
                || !StringUtils.hasText(course.getDescription())) {
            return null;
        }
        return courseRepository.save(course);
    }

    // GET: 전체 조회
    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    // GET: 단건 조회
    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    // PUT: 전체 수정 (모든 필드 필수)
    @Override
    public Course update(Long id, Course course) {
        Course existing = courseRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        if (!StringUtils.hasText(course.getName())
                || !StringUtils.hasText(course.getCategory())
                || !StringUtils.hasText(course.getDescription())) {
            return null;
        }
        existing.update(course.getName(), course.getCategory(), course.getRating(), course.getDescription());
        return courseRepository.save(existing);
    }

    // PATCH: 부분 수정 (전달된 필드만 반영)
    @Override
    public Course patch(Long id, Course course) {
        Course existing = courseRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.patch(course.getName(), course.getCategory(), course.getRating(), course.getDescription());
        return courseRepository.save(existing);
    }

    // DELETE: 삭제
    @Override
    public boolean delete(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
