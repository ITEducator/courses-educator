package com.iteducator.courses.controller;

import com.iteducator.courses.model.Course;
import com.iteducator.courses.service.CourseService;
import com.iteducator.courses.service.PhotoService;
import com.iteducator.courses.util.ValidationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final PhotoService photoService;
    private final CourseService courseService;
    private final ValidationService validationService;

    public CourseController(PhotoService photoService,
                            CourseService courseService,
                            ValidationService validationService) {
        this.photoService = photoService;
        this.courseService = courseService;
        this.validationService = validationService;
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
        return new ResponseEntity<>(String.format("Course with ID: %s was deleted", id),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestPart Course course,
                                          BindingResult result,
                                          @RequestPart final MultipartFile image) {
        ResponseEntity<?> errorMap = validationService.mapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }
        course.setImage(photoService.convertToPhoto(image));
        Course createdCourse = courseService.saveOrUpdateCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }
}
