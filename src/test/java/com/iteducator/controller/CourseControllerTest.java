package com.iteducator.controller;

import com.google.gson.Gson;
import com.iteducator.courses.controller.CourseController;
import com.iteducator.courses.exception.CourseException;
import com.iteducator.courses.exception.handler.CustomResponseEntityExceptionHandler;
import com.iteducator.courses.model.Course;
import com.iteducator.courses.model.Photo;
import com.iteducator.courses.model.User;
import com.iteducator.courses.repository.CourseRepository;
import com.iteducator.courses.repository.PhotoRepository;
import com.iteducator.courses.service.CourseService;
import com.iteducator.courses.service.PhotoService;
import com.iteducator.courses.util.ValidationService;
import net.minidev.json.JSONObject;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CourseControllerTest {

    private MockMvc mockMvc;
    private List<Course> courses;
    private PhotoService photoService;
    private CourseRepository courseRepository;

    @Before
    public void setup() {
        photoService = mock(PhotoService.class);
        courseRepository = mock(CourseRepository.class);
        CourseService courseService = new CourseService(photoService, courseRepository);

        courses = generateCourses();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new CourseController(
                        photoService, courseService, new ValidationService()))
                .build();
    }

    @Test
    public void testGetCourses() throws Exception {
        Mockito.when(courseRepository.findAll()).thenReturn(courses);
        this.mockMvc.perform(get("/api/courses/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(courses)))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(courseRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void testGetCourse() throws Exception {
        Course course = courses.get(1);
        Mockito.when(courseRepository.findById("1")).thenReturn(Optional.ofNullable(course));
        this.mockMvc.perform(get("/api/courses/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(course)))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(courseRepository, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void testGetCourseWithWrongId() throws Exception {
        Mockito.when(courseRepository.findById("500")).thenThrow(CourseException.class);
        MvcResult result = this.mockMvc.perform(get("/api/courses/{id}/", "500"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Optional<CourseException> exception = Optional.ofNullable(
                (CourseException) result.getResolvedException());
        exception.ifPresent((e) -> Assert.assertThat(e, is(notNullValue())));
        exception.ifPresent((e) -> Assert.assertThat(e, is(instanceOf(CourseException.class))));
        Mockito.verify(courseRepository, Mockito.times(1)).findById("500");
        Mockito.verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void testDeleteCourse() throws Exception {
        Course course = courses.get(0);
        Mockito.when(courseRepository.findById("0")).thenReturn(Optional.ofNullable(course));
        this.mockMvc.perform(delete("/api/courses/{id}", "0"))
                .andExpect(status().isOk());

        assert course != null;
        Mockito.verify(courseRepository, Mockito.times(1)).findById("0");
        Mockito.verify(courseRepository, Mockito.times(1)).delete(course);
        Mockito.verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void testDeleteCourseWithWrongId() throws Exception {
        Mockito.when(courseRepository.findById("500")).thenThrow(CourseException.class);
        MvcResult result = this.mockMvc.perform(delete("/api/courses/{id}", "500"))
                .andExpect(status().isBadRequest())
                .andReturn();

        Optional<CourseException> exception = Optional.ofNullable(
                (CourseException) result.getResolvedException());
        exception.ifPresent((e) -> Assert.assertThat(e, is(notNullValue())));
        exception.ifPresent((e) -> Assert.assertThat(e, is(instanceOf(CourseException.class))));
        Mockito.verify(courseRepository, Mockito.times(1)).findById("500");
        Mockito.verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void testCreateCourse() throws Exception {
        Course course = courses.get(1);
        MockMultipartFile image = new MockMultipartFile("image", "filename.png",
                "text/plain", "1".getBytes());
        MockMultipartFile courseJson = new MockMultipartFile("course", "",
                "application/json", new Gson().toJson(course).getBytes());

        Mockito.when(photoService.convertToPhoto(image)).thenReturn(new Photo());
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/courses")
                .file(image)
                .file(courseJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn();

        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
        Mockito.verify(courseRepository, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void testErrorsCreateOrUpdateCourse() throws Exception {
        Course course = new Course();
        MockMultipartFile image = new MockMultipartFile("image", "filename.png",
                "text/plain", "1".getBytes());
        MockMultipartFile courseJson = new MockMultipartFile("course", "",
                "application/json", new Gson().toJson(course).getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/courses")
                .file(image)
                .file(courseJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private List<Course> generateCourses() {
        List<Course> courses = new ArrayList<>();

        Course course = new Course("0", "title", "subtitle", new BigDecimal(1500), "IT",
                "description", new User(), new Photo(), Arrays.asList("java", "react"));
        Course course2 = new Course("1", "title2", "subtitle2", new BigDecimal(2500), "FINANCE",
                "description2", new User(), new Photo(), Arrays.asList("js", "angular"));

        courses.add(course);
        courses.add(course2);

        return courses;
    }
}
