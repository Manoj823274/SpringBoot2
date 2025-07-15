
package com.example.school.controller;

import com.example.school.dto.StudentDTO;
import com.example.school.dto.StudentDetailsDTO;
import com.example.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO create(@Valid @RequestBody StudentDTO dto) {
        return studentService.create(dto);
    }

    @GetMapping("/{id}")
    public StudentDTO get(@PathVariable Long id) {
        return studentService.get(id);
    }

    @GetMapping
    public List<StudentDTO> list() {
        return studentService.list();
    }

    @PutMapping("/{id}")
    public StudentDTO update(@PathVariable Long id, @Valid @RequestBody StudentDTO dto) {
        return studentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }

    @GetMapping("/{id}/details")
    public StudentDetailsDTO details(@PathVariable Long id) {
        return studentService.fetchDetails(id);
    }
}
