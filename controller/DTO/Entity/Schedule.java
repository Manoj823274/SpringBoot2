
package com.example.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "dayOfWeek", "startTime", "endTime"})
})
public class Schedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotBlank
    private String subject;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Student student;
}
