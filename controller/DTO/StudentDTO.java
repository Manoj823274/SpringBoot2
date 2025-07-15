
package com.example.school.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
}
