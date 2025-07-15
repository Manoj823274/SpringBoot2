
package com.example.school.mapper;

import com.example.school.dto.*;
import com.example.school.entity.*;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO toDto(Student entity);
    Student toEntity(StudentDTO dto);

    List<StudentDTO> toDtoList(List<Student> entities);
}
