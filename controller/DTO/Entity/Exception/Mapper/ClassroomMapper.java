
package com.example.school.mapper;

import com.example.school.dto.*;
import com.example.school.entity.*;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

    ClassroomDTO toDto(Classroom entity);
    Classroom toEntity(ClassroomDTO dto);

    List<ClassroomDTO> toDtoList(List<Classroom> entities);
}
