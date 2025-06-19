package com.epi.coursemanagement.service;

import com.epi.coursemanagement.model.Course;
import com.epi.coursemanagement.model.Student;
import com.epi.coursemanagement.repository.CourseRepository;
import com.epi.coursemanagement.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        logger.info("Enrolling student ID {} in course ID {}", studentId, courseId);
        
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
                
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        
        // Check if already enrolled
        if (student.getEnrolledCourses().contains(course)) {
            logger.warn("Student is already enrolled in this course");
            throw new IllegalStateException("Student is already enrolled in this course");
        }
        
        student.getEnrolledCourses().add(course);
        studentRepository.save(student);
        logger.info("Student successfully enrolled in course");
    }

    @Transactional
    public void unenrollStudentFromCourse(Long studentId, Long courseId) {
        logger.info("Unenrolling student ID {} from course ID {}", studentId, courseId);
        
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
                
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        
        // Check if enrolled
        if (!student.getEnrolledCourses().contains(course)) {
            logger.warn("Student is not enrolled in this course");
            throw new IllegalStateException("Student is not enrolled in this course");
        }
        
        student.getEnrolledCourses().remove(course);
        studentRepository.save(student);
        logger.info("Student successfully unenrolled from course");
    }
}
