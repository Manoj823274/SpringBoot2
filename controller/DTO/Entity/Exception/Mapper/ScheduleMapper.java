
package com.example.school.mapper;

import com.example.school.dto.*;
import com.example.school.entity.*;
import org.mapstruct.*;
import java.time.DayOfWeek;
import java.util.List;

@Mapper(componentModel = "spring", imports = DayOfWeek.class)
public interface ScheduleMapper {

    @Mapping(target = "dayOfWeek", expression = "java(entity.getDayOfWeek().name())")
    ScheduleDTO toDto(Schedule entity);

    @Mapping(target = "dayOfWeek", expression = "java(DayOfWeek.valueOf(dto.getDayOfWeek()))")
    @Mapping(target = "student", ignore = true)
    Schedule toEntity(ScheduleDTO dto);

    List<ScheduleDTO> toDtoList(List<Schedule> entities);
}
