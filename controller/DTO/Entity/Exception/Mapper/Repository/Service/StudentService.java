
package com.example.school.service;

import com.example.school.dto.StudentDTO;
import com.example.school.dto.StudentDetailsDTO;
import com.example.school.entity.Student;
import com.example.school.entity.Assignment;
import com.example.school.repository.StudentRepository;
import com.example.school.repository.AssignmentRepository;
import com.example.school.mapper.StudentMapper;
import com.example.school.mapper.ScheduleMapper;
import com.example.school.mapper.ClassroomMapper;
import com.example.school.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepo;
    private final AssignmentRepository assignmentRepo;
    private final StudentMapper studentMapper;
    private final ScheduleMapper scheduleMapper;
    private final ClassroomMapper classroomMapper;

    public StudentDTO create(StudentDTO dto) {
        Student saved = studentRepo.save(studentMapper.toEntity(dto));
        return studentMapper.toDto(saved);
    }

    public StudentDTO get(Long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        return studentMapper.toDto(student);
    }

    public java.util.List<StudentDTO> list() {
        return studentMapper.toDtoList(studentRepo.findAll());
    }

    public StudentDTO update(Long id, StudentDTO dto) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setDob(dto.getDob());
        return studentMapper.toDto(studentRepo.save(student));
    }

    public void delete(Long id) {
        if (!studentRepo.existsById(id))
            throw new NotFoundException("Student not found");
        studentRepo.deleteById(id);
    }

    public StudentDetailsDTO fetchDetails(Long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        Assignment asg = assignmentRepo.findByStudentId(id).orElse(null);

        StudentDetailsDTO details = new StudentDetailsDTO();
        details.setStudent(studentMapper.toDto(student));
        details.setSchedules(scheduleMapper.toDtoList(student.getSchedules()));
        details.setClassroom(asg != null ? classroomMapper.toDto(asg.getClassroom()) : null);
        return details;
    }
}
