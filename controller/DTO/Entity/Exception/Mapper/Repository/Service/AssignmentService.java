
package com.example.school.service;

import com.example.school.dto.StudentDTO;
import com.example.school.entity.Assignment;
import com.example.school.entity.Classroom;
import com.example.school.entity.Student;
import com.example.school.exception.BadRequestException;
import com.example.school.exception.NotFoundException;
import com.example.school.mapper.StudentMapper;
import com.example.school.repository.AssignmentRepository;
import com.example.school.repository.ClassroomRepository;
import com.example.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentService {

    private final AssignmentRepository assignmentRepo;
    private final StudentRepository studentRepo;
    private final ClassroomRepository classroomRepo;
    private final StudentMapper studentMapper;

    public void assign(Long studentId, Long classroomId) {
        if (assignmentRepo.existsByStudentId(studentId))
            throw new BadRequestException("Student already assigned");

        Classroom classroom = classroomRepo.findById(classroomId)
                .orElseThrow(() -> new NotFoundException("Classroom not found"));

        long used = assignmentRepo.countByClassroomId(classroomId);
        if (used >= classroom.getCapacity())
            throw new BadRequestException("Classroom is full");

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        assignmentRepo.save(Assignment.builder()
                .student(student)
                .classroom(classroom)
                .build());
    }

    public void update(Long assignmentId, Long newClassroomId) {
        Assignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Assignment not found"));
        Classroom classroom = classroomRepo.findById(newClassroomId)
                .orElseThrow(() -> new NotFoundException("Classroom not found"));

        long used = assignmentRepo.countByClassroomId(newClassroomId);
        if (used >= classroom.getCapacity())
            throw new BadRequestException("Classroom is full");

        assignment.setClassroom(classroom);
    }

    public void remove(Long assignmentId) {
        if (!assignmentRepo.existsById(assignmentId))
            throw new NotFoundException("Assignment not found");
        assignmentRepo.deleteById(assignmentId);
    }

    public List<StudentDTO> studentsInClassroom(Long classroomId) {
        Classroom classroom = classroomRepo.findById(classroomId)
                .orElseThrow(() -> new NotFoundException("Classroom not found"));

        return assignmentRepo.findAll().stream()
                .filter(a -> a.getClassroom().getId().equals(classroomId))
                .map(Assignment::getStudent)
                .map(studentMapper::toDto)
                .toList();
    }

    public Classroom getClassroomOfStudent(Long studentId) {
        return assignmentRepo.findByStudentId(studentId)
                .orElseThrow(() -> new NotFoundException("Student not assigned"))
                .getClassroom();
    }
}
