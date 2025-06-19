package com.epi.coursemanagement.controller.api;

import com.epi.coursemanagement.model.Instructor;
import com.epi.coursemanagement.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instructors")
@Tag(name = "Instructor API", description = "API for managing instructors")
public class InstructorApiController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorApiController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    @Operation(summary = "Get all instructors", description = "Returns a list of all instructors")
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        List<Instructor> instructors = instructorService.findAll();
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/paged")
    @Operation(summary = "Get paged instructors", description = "Returns a page of instructors")
    public ResponseEntity<Page<Instructor>> getPagedInstructors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Instructor> instructorPage = instructorService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(instructorPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get instructor by ID", description = "Returns an instructor by its ID")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Long id) {
        Optional<Instructor> instructor = instructorService.findById(id);
        return instructor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new instructor", description = "Creates a new instructor")
    public ResponseEntity<Instructor> createInstructor(@Valid @RequestBody Instructor instructor) {
        Instructor savedInstructor = instructorService.save(instructor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInstructor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an instructor", description = "Updates an existing instructor")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @Valid @RequestBody Instructor instructor) {
        if (!instructorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        instructor.setId(id);
        Instructor updatedInstructor = instructorService.save(instructor);
        return ResponseEntity.ok(updatedInstructor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an instructor", description = "Deletes an instructor by its ID")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        if (!instructorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        instructorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
