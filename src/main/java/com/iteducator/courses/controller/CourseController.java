package com.iteducator.courses.controller;

import com.iteducator.courses.model.Course;
import com.iteducator.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/all")
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @PostMapping("/add-random")
    public Course addRandomCourse(){
        Course course = new Course(UUID.randomUUID().toString(), "First course");
        return courseRepository.save(course);
    }
}
