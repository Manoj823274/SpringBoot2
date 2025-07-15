
package com.example.school.controller;

import com.example.school.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void assign(@RequestParam Long studentId, @RequestParam Long classroomId) {
        assignmentService.assign(studentId, classroomId);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestParam Long classroomId) {
        assignmentService.update(id, classroomId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        assignmentService.remove(id);
    }
}
