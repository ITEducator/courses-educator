package com.iteducator.courses.controller;

import com.google.gson.Gson;
import com.iteducator.courses.model.Course;
import com.iteducator.courses.model.Photo;
import com.iteducator.courses.service.CourseService;
import com.iteducator.courses.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final PhotoService photoService;
    private final CourseService courseService;

    public CourseController(PhotoService photoService,
                            CourseService courseService) {
        this.photoService = photoService;
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getCourses() {
        List<Course> courses = courseService.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable String id) {
        Course course = courseService.findById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id) {
        courseService.deleteById(id);
        return new ResponseEntity<>("Course with ID: "
                .concat(id).concat(" was deleted"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestParam String course,
                                          @RequestParam final MultipartFile image) {
        Photo photo = photoService.createPhoto(image);
        Course courseObj = new Gson().fromJson(course, Course.class);
        courseObj.setImage(photo);
        Course createdCourse = courseService.saveOrUpdateCourse(courseObj);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }
}
