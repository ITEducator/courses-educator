package com.iteducator.courses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
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
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "10000.0")
    private BigDecimal price;
    @NotBlank(message = "Course category is required")
    private String category;
    @NotBlank(message = "Course description is required")
    private String description;
    @JsonIgnore
    private User author;
    private Photo image;
    private List<@NotBlank(message = "Course requirements is required") String> requirements;
}
