package com.epi.coursemanagement.controller;

import com.epi.coursemanagement.model.Course;
import com.epi.coursemanagement.model.Student;
import com.epi.coursemanagement.service.CourseService;
import com.epi.coursemanagement.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String listStudents(Model model, @RequestParam(defaultValue = "0") int page, 
                              @RequestParam(defaultValue = "5") int size) {
        logger.info("Listing students with page={}, size={}", page, size);
        Page<Student> studentPage = studentService.findAll(PageRequest.of(page, size));
        
        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalItems", studentPage.getTotalElements());
        
        return "students/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        logger.info("Showing create student form");
        model.addAttribute("student", new Student());
        return "students/form";
    }

    @PostMapping
    public String createStudent(@Valid @ModelAttribute Student student, BindingResult result,
                               RedirectAttributes redirectAttributes) {
        logger.info("Creating new student: {} {}", student.getFirstName(), student.getLastName());
        if (result.hasErrors()) {
            logger.warn("Validation errors in student form: {}", result.getAllErrors());
            return "students/form";
        }
        
        studentService.save(student);
        redirectAttributes.addFlashAttribute("success", "Student created successfully!");
        
        return "redirect:/students";
    }

    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        logger.info("Viewing student with id: {}", id);
        Optional<Student> student = studentService.findById(id);
        
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "students/view";
        } else {
            logger.warn("Student not found with id: {}", id);
            return "redirect:/students";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        logger.info("Showing edit form for student id: {}", id);
        Optional<Student> student = studentService.findById(id);
        
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "students/form";
        } else {
            logger.warn("Student not found with id: {}", id);
            return "redirect:/students";
        }
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute Student student,
                              BindingResult result, RedirectAttributes redirectAttributes) {
        logger.info("Updating student with id: {}", id);
        if (result.hasErrors()) {
            logger.warn("Validation errors in student update form: {}", result.getAllErrors());
            return "students/form";
        }
        
        student.setId(id); // Ensure ID is set for update
        studentService.save(student);
        redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
        
        return "redirect:/students";
    }

    @GetMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.info("Deleting student with id: {}", id);
        studentService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        
        return "redirect:/students";
    }

    @GetMapping("/{id}/courses")
    public String viewEnrolledCourses(@PathVariable Long id, Model model) {
        logger.info("Viewing enrolled courses for student id: {}", id);
        Optional<Student> student = studentService.findById(id);
        
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            List<Course> availableCourses = courseService.findAll();
            model.addAttribute("availableCourses", availableCourses);
            return "students/courses";
        } else {
            logger.warn("Student not found with id: {}", id);
            return "redirect:/students";
        }
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public String enrollInCourse(@PathVariable Long studentId, @PathVariable Long courseId,
                                RedirectAttributes redirectAttributes) {
        logger.info("Enrolling student id: {} in course id: {}", studentId, courseId);
        try {
            studentService.enrollStudentInCourse(studentId, courseId);
            redirectAttributes.addFlashAttribute("success", "Student enrolled in course successfully!");
        } catch (Exception e) {
            logger.error("Error enrolling student in course: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error enrolling student: " + e.getMessage());
        }
        
        return "redirect:/students/" + studentId + "/courses";
    }

    @PostMapping("/{studentId}/unenroll/{courseId}")
    public String unenrollFromCourse(@PathVariable Long studentId, @PathVariable Long courseId,
                                   RedirectAttributes redirectAttributes) {
        logger.info("Unenrolling student id: {} from course id: {}", studentId, courseId);
        try {
            studentService.unenrollStudentFromCourse(studentId, courseId);
            redirectAttributes.addFlashAttribute("success", "Student unenrolled from course successfully!");
        } catch (Exception e) {
            logger.error("Error unenrolling student from course: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error unenrolling student: " + e.getMessage());
        }
        
        return "redirect:/students/" + studentId + "/courses";
    }
}
