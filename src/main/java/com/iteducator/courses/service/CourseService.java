package com.iteducator.courses.service;

import com.iteducator.courses.exception.CourseException;
import com.iteducator.courses.model.Course;
import com.iteducator.courses.repository.CourseRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final PhotoService photoService;
    private final CourseRepository courseRepository;

    public CourseService(PhotoService photoService,
                         CourseRepository courseRepository) {
        this.photoService = photoService;
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseException(
                        String.format("Course with ID - %s doesn't exist", id)));

        // validate that course exists in current user's library...

        return course;
    }

    public void deleteById(String id) {
        courseRepository.delete(findById(id));
    }

    public Course saveOrUpdateCourse(Course course) {
        return courseRepository.findById(course.getId())
                .map(existingCourse -> {
                    existingCourse.setTitle(course.getTitle());
                    existingCourse.setPrice(course.getPrice());
                    existingCourse.setAuthor(course.getAuthor());
                    existingCourse.setSubtitle(course.getSubtitle());
                    existingCourse.setCategory(course.getCategory());
                    existingCourse.setDescription(course.getDescription());
                    existingCourse.setRequirements(course.getRequirements());
                    existingCourse.setImage(photoService.overrideImage(
                            course.getImage(), existingCourse.getImage()));
                    return courseRepository.save(existingCourse);
                })
                .orElseGet(() -> {
                    course.setId(course.getId() != null
                            ? course.getId()
                            : UUID.randomUUID().toString());
                    photoService.validateImage(course.getImage());
                    return courseRepository.save(course);
                });
    }
}
