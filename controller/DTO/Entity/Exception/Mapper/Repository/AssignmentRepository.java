
package com.example.school.repository;

import com.example.school.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    boolean existsByStudentId(Long studentId);
    long countByClassroomId(Long classroomId);
    Optional<Assignment> findByStudentId(Long studentId);
}
