
package com.example.school.service;

import com.example.school.dto.ScheduleDTO;
import com.example.school.entity.Schedule;
import com.example.school.entity.Student;
import com.example.school.exception.BadRequestException;
import com.example.school.exception.NotFoundException;
import com.example.school.mapper.ScheduleMapper;
import com.example.school.repository.ScheduleRepository;
import com.example.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final StudentRepository studentRepo;
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO add(Long studentId, ScheduleDTO dto) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Schedule schedule = scheduleMapper.toEntity(dto);
        schedule.setStudent(student);

        validateNoOverlap(studentId, schedule.getDayOfWeek(), schedule.getStartTime(), schedule.getEndTime(), null);

        Schedule saved = scheduleRepo.save(schedule);
        return scheduleMapper.toDto(saved);
    }

    public ScheduleDTO update(Long studentId, Long scheduleId, ScheduleDTO dto) {
        Schedule schedule = scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        if (!schedule.getStudent().getId().equals(studentId))
            throw new BadRequestException("Schedule does not belong to student");

        schedule.setDayOfWeek(DayOfWeek.valueOf(dto.getDayOfWeek()));
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setSubject(dto.getSubject());

        validateNoOverlap(studentId, schedule.getDayOfWeek(), schedule.getStartTime(), schedule.getEndTime(), scheduleId);

        return scheduleMapper.toDto(scheduleRepo.save(schedule));
    }

    public void delete(Long studentId, Long scheduleId) {
        Schedule schedule = scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));
        if (!schedule.getStudent().getId().equals(studentId))
            throw new BadRequestException("Schedule does not belong to student");
        scheduleRepo.delete(schedule);
    }

    public List<ScheduleDTO> list(Long studentId) {
        if (!studentRepo.existsById(studentId))
            throw new NotFoundException("Student not found");
        return scheduleMapper.toDtoList(scheduleRepo.findByStudentId(studentId));
    }

    private void validateNoOverlap(Long studentId, java.time.DayOfWeek dow, LocalTime start, LocalTime end, Long excludeId) {
        for (Schedule s : scheduleRepo.findByStudentId(studentId)) {
            if (excludeId != null && s.getId().equals(excludeId)) continue;
            if (s.getDayOfWeek() == dow && timesOverlap(s.getStartTime(), s.getEndTime(), start, end)) {
                throw new BadRequestException("Schedule overlaps an existing entry");
            }
        }
    }

    private boolean timesOverlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        return !s1.isAfter(e2) && !s2.isAfter(e1);
    }
}
