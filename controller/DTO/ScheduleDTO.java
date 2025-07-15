
package com.example.school.dto;

import lombok.*;
import java.time.*;

@Getter @Setter
public class ScheduleDTO {
    private Long id;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String subject;
}
