
package com.example.school.controller;

import com.example.school.dto.ClassroomDTO;
import com.example.school.dto.StudentDTO;
import com.example.school.service.ClassroomService;
import com.example.school.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;
    private final AssignmentService assignmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClassroomDTO create(@Valid @RequestBody ClassroomDTO dto) {
        return classroomService.create(dto);
    }

    @GetMapping("/{id}")
    public ClassroomDTO get(@PathVariable Long id) {
        return classroomService.get(id);
    }

    @GetMapping
    public List<ClassroomDTO> list() {
        return classroomService.list();
    }

    @PutMapping("/{id}")
    public ClassroomDTO update(@PathVariable Long id, @Valid @RequestBody ClassroomDTO dto) {
        return classroomService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        classroomService.delete(id);
    }

    @GetMapping("/{id}/students")
    public List<StudentDTO> students(@PathVariable Long id) {
        return assignmentService.studentsInClassroom(id);
    }
}
