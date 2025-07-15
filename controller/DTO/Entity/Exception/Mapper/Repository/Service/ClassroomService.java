
package com.example.school.service;

import com.example.school.dto.ClassroomDTO;
import com.example.school.entity.Classroom;
import com.example.school.entity.Assignment;
import com.example.school.exceptions.*;
import com.example.school.repository.ClassroomRepository;
import com.example.school.repository.AssignmentRepository;
import com.example.school.mapper.ClassroomMapper;
import com.example.school.mapper.StudentMapper;
import com.example.school.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassroomService {

    private final ClassroomRepository classroomRepo;
    private final AssignmentRepository assignmentRepo;
    private final ClassroomMapper classroomMapper;
    private final StudentMapper studentMapper;

    public ClassroomDTO create(ClassroomDTO dto) {
        Classroom saved = classroomRepo.save(classroomMapper.toEntity(dto));
        return classroomMapper.toDto(saved);
    }

    public ClassroomDTO get(Long id) {
        Classroom classroom = classroomRepo.findById(id)
                .orElseThrow(() -> new com.example.school.exception.NotFoundException("Classroom not found"));
        return classroomMapper.toDto(classroom);
    }

    public List<ClassroomDTO> list() {
        return classroomMapper.toDtoList(classroomRepo.findAll());
    }

    public ClassroomDTO update(Long id, ClassroomDTO dto) {
        Classroom classroom = classroomRepo.findById(id)
                .orElseThrow(() -> new com.example.school.exception.NotFoundException("Classroom not found"));
        classroom.setName(dto.getName());
        classroom.setCapacity(dto.getCapacity());
        return classroomMapper.toDto(classroomRepo.save(classroom));
    }

    public void delete(Long id) {
        if (!classroomRepo.existsById(id))
            throw new com.example.school.exception.NotFoundException("Classroom not found");
        classroomRepo.deleteById(id);
    }

    public List<Student> studentsInClassroom(Long classroomId) {
        return assignmentRepo.findAll().stream()
                .filter(a -> a.getClassroom().getId().equals(classroomId))
                .map(Assignment::getStudent)
                .toList();
    }
}
