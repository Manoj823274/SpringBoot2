
package com.example.school.dto;

import lombok.*;
import java.util.List;

@Getter @Setter
public class StudentDetailsDTO {
    private StudentDTO student;
    private ClassroomDTO classroom;
    private List<ScheduleDTO> schedules;
}
