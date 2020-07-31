package com.iteducator.courses.service;

import com.iteducator.courses.model.Course;
import com.iteducator.courses.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void deleteProjectByIdentifier(String id) {
        courseRepository.deleteById(id);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(String id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            throw new RuntimeException("Course ID - ".concat(id).concat(" doesn't exist"));
        }

        // validate that course exists in user's library...

        return course.get();
    }

    public void deleteById(String id) {
        Course course = findById(id);

        courseRepository.delete(course);
    }

    public Course saveOrUpdateCourse(Course course) {
        return courseRepository.findById(course.getId())
                .map(existingCourse -> {
                    existingCourse.setTitle(course.getTitle());
                    return courseRepository.save(existingCourse);
                })
                .orElseGet(() -> {
                    String id = course.getId() == null ? UUID.randomUUID().toString() : course.getId();
                    course.setId(id);
                    return courseRepository.save(course);
                });
    }
}
