package com.epi.coursemanagement.controller;

import com.epi.coursemanagement.model.Category;
import com.epi.coursemanagement.model.Course;
import com.epi.coursemanagement.model.Instructor;
import com.epi.coursemanagement.service.CategoryService;
import com.epi.coursemanagement.service.CourseService;
import com.epi.coursemanagement.service.InstructorService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;
    private final CategoryService categoryService;
    private final InstructorService instructorService;

    @Autowired
    public CourseController(CourseService courseService, CategoryService categoryService, InstructorService instructorService) {
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.instructorService = instructorService;
    }

    @GetMapping
    public String listCourses(Model model, @RequestParam(defaultValue = "0") int page, 
                             @RequestParam(defaultValue = "5") int size) {
        logger.info("Listing courses with page={}, size={}", page, size);
        Page<Course> coursePage = courseService.findAll(PageRequest.of(page, size));
        
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("totalItems", coursePage.getTotalElements());
        
        return "courses/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        logger.info("Showing create course form");
        List<Category> categories = categoryService.findAll();
        List<Instructor> instructors = instructorService.findAll();
        
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categories);
        model.addAttribute("instructors", instructors);
        
        return "courses/form";
    }

    @PostMapping
    public String createCourse(@Valid @ModelAttribute Course course, BindingResult result,
                          @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
                          Model model, RedirectAttributes redirectAttributes) {
        logger.info("Creating new course: {}", course.getTitle());
        if (result.hasErrors()) {
            logger.warn("Validation errors in course form: {}", result.getAllErrors());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("instructors", instructorService.findAll());
            return "courses/form";
        }
        
        courseService.save(course, thumbnail);
        redirectAttributes.addFlashAttribute("success", "Course created successfully!");
        
        return "redirect:/courses";
    }

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        logger.info("Viewing course with id: {}", id);
        Optional<Course> course = courseService.findById(id);
        
        if (course.isPresent()) {
            model.addAttribute("course", course.get());
            return "courses/view";
        } else {
            logger.warn("Course not found with id: {}", id);
            return "redirect:/courses";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        logger.info("Showing edit form for course id: {}", id);
        Optional<Course> course = courseService.findById(id);
        
        if (course.isPresent()) {
            model.addAttribute("course", course.get());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("instructors", instructorService.findAll());
            return "courses/form";
        } else {
            logger.warn("Course not found with id: {}", id);
            return "redirect:/courses";
        }
    }

    @PostMapping("/{id}")
    public String updateCourse(@PathVariable Long id, @Valid @ModelAttribute Course course,
                          BindingResult result, @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
                          Model model, RedirectAttributes redirectAttributes) {
        logger.info("Updating course with id: {}", id);
        if (result.hasErrors()) {
            logger.warn("Validation errors in course update form: {}", result.getAllErrors());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("instructors", instructorService.findAll());
            return "courses/form";
        }
        
        course.setId(id); // Ensure ID is set for update
        courseService.update(course, thumbnail);
        redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
        
        return "redirect:/courses";
    }

    @GetMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.info("Deleting course with id: {}", id);
        courseService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        
        return "redirect:/courses";
    }
}
