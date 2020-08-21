package com.iteducator.courses.controller;

import com.google.gson.Gson;
import com.iteducator.courses.model.Photo;
import com.iteducator.courses.model.User;
import com.iteducator.courses.service.PhotoService;
import com.iteducator.courses.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PhotoService photoService;

    public UserController(UserService userService,
                          PhotoService photoService) {
        this.userService = userService;
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestParam String user,
                                        @RequestParam MultipartFile image) {
        Photo photo = photoService.createPhoto(image);
        User userObj = new Gson().fromJson(user, User.class);
        userObj.setAvatar(photo);
        User createdUser = userService.createUser(userObj);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getCourses() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
