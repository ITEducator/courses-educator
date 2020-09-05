package com.iteducator.courses.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "courses")
public class Course {

    @Id
    private String id;
    @NotBlank(message = "Course title is required")
    private String title;
    @NotBlank(message = "Course subtitle is required")
    private String subtitle;
    @Min(value = 0)
    @Max(value = 100_000)
    @NotNull(message = "Course price is required")
    private BigDecimal price;
    @NotBlank(message = "Course category is required")
    private String category;
    @NotBlank(message = "Course description is required")
    private String description;
    private User author;
    private Photo image;
    @NotEmpty(message = "Course requirements is required")
    private List<String> requirements;
}
