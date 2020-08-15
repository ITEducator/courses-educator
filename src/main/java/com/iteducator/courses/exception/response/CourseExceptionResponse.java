package com.iteducator.courses.exception.response;

public class CourseExceptionResponse {

    private String courseNotFound;

    public CourseExceptionResponse(String courseNotFound) {
        this.courseNotFound = courseNotFound;
    }

    public String getCourseNotFound() {
        return courseNotFound;
    }

    public void setCourseNotFound(String courseNotFound) {
        this.courseNotFound = courseNotFound;
    }
}
