
package com.example.school.controller;

import com.example.school.dto.ScheduleDTO;
import com.example.school.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students/{studentId}/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDTO add(@PathVariable Long studentId, @Valid @RequestBody ScheduleDTO dto) {
        return scheduleService.add(studentId, dto);
    }

    @PutMapping("/{scheduleId}")
    public ScheduleDTO update(@PathVariable Long studentId,
                              @PathVariable Long scheduleId,
                              @Valid @RequestBody ScheduleDTO dto) {
        return scheduleService.update(studentId, scheduleId, dto);
    }

    @DeleteMapping("/{scheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long studentId,
                       @PathVariable Long scheduleId) {
        scheduleService.delete(studentId, scheduleId);
    }

    @GetMapping
    public List<ScheduleDTO> list(@PathVariable Long studentId) {
        return scheduleService.list(studentId);
    }
}
