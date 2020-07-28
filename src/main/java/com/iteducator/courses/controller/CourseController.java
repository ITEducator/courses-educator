package com.iteducator.courses.controller;

import com.iteducator.courses.model.Course;
import com.iteducator.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses/all")
    public List<Course> getCourses(){
        courseRepository.save(new Course("First course"));
        List<Course> courses = courseRepository.findAll();
        courses.forEach(System.out::println);
        return courses;
    }
}
