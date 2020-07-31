package com.iteducator.courses.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String mail;
    private String password;
    private String firstName;
    private String lastName;
    private String biography;
    private byte[] avatar;
    private List<Course> courses;
    private List<Course> purchases;
}
