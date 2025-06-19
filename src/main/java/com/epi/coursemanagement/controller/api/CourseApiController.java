package com.epi.coursemanagement.controller.api;

import com.epi.coursemanagement.model.Course;
import com.epi.coursemanagement.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course API", description = "API for managing courses")
public class CourseApiController {

    private final CourseService courseService;

    @Autowired
    public CourseApiController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(summary = "Get all courses", description = "Returns a list of all courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/paged")
    @Operation(summary = "Get paged courses", description = "Returns a page of courses")
    public ResponseEntity<Page<Course>> getPagedCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Course> coursePage = courseService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(coursePage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Returns a course by its ID")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.findById(id);
        return course.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get courses by category", description = "Returns courses by category ID")
    public ResponseEntity<Page<Course>> getCoursesByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Course> courses = courseService.findByCategoryId(categoryId, PageRequest.of(page, size));
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/instructor/{instructorId}")
    @Operation(summary = "Get courses by instructor", description = "Returns courses by instructor ID")
    public ResponseEntity<Page<Course>> getCoursesByInstructor(
            @PathVariable Long instructorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Course> courses = courseService.findByInstructorId(instructorId, PageRequest.of(page, size));
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    @Operation(summary = "Create a new course", description = "Creates a new course")
    public ResponseEntity<Course> createCourse(@Valid @RequestPart("course") Course course,
                                             @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) {
        Course savedCourse = courseService.save(course, thumbnail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course", description = "Updates an existing course")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id,
                                             @Valid @RequestPart("course") Course course,
                                             @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) {
        if (!courseService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        course.setId(id);
        Course updatedCourse = courseService.update(course, thumbnail);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course", description = "Deletes a course by its ID")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
