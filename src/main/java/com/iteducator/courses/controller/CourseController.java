package com.iteducator.courses.controller;

import com.iteducator.courses.model.Course;
import com.iteducator.courses.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public List<Course> getCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable String id) {
        Course course = courseService.findById(id);

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id) {
        courseService.deleteById(id);

        return new ResponseEntity<>("Course with ID: ".concat(id).concat(" was deleted"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.saveOrUpdateCourse(course);

        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PostMapping("/add-random")
    public Course addRandomCourse() {
        Course course = new Course(UUID.randomUUID().toString(), "First course");
        return courseService.saveOrUpdateCourse(course);
    }
}
