package com.example.ex06.repository;

import com.example.ex06.entity.Course;
import org.springframework.data.repository.CrudRepository;

interface CourseRepository extends CrudRepository<Course, Long> {
}
