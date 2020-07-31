package com.iteducator.courses.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "courses")
public class Course {

    @Id
    private String id;
    private String title;
    private String subtitle;
    private String category;
    private String description;
    private byte[] image;
    private User author;
    private BigDecimal price;
    private List<String> requirements;
}
