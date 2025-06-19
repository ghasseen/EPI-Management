package com.epi.coursemanagement.controller;

import com.epi.coursemanagement.model.Instructor;
import com.epi.coursemanagement.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public String listInstructors(Model model, @RequestParam(defaultValue = "0") int page, 
                                 @RequestParam(defaultValue = "5") int size) {
        Page<Instructor> instructorPage = instructorService.findAll(PageRequest.of(page, size));
        
        model.addAttribute("instructors", instructorPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", instructorPage.getTotalPages());
        model.addAttribute("totalItems", instructorPage.getTotalElements());
        
        return "instructors/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructors/form";
    }

    @PostMapping
    public String createInstructor(@Valid @ModelAttribute Instructor instructor, BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "instructors/form";
        }
        
        instructorService.save(instructor);
        redirectAttributes.addFlashAttribute("success", "Instructor created successfully!");
        
        return "redirect:/instructors";
    }

    @GetMapping("/{id}")
    public String viewInstructor(@PathVariable Long id, Model model) {
        Optional<Instructor> instructor = instructorService.findById(id);
        
        if (instructor.isPresent()) {
            model.addAttribute("instructor", instructor.get());
            return "instructors/view";
        } else {
            return "redirect:/instructors";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Instructor> instructor = instructorService.findById(id);
        
        if (instructor.isPresent()) {
            model.addAttribute("instructor", instructor.get());
            return "instructors/form";
        } else {
            return "redirect:/instructors";
        }
    }

    @PostMapping("/{id}")
    public String updateInstructor(@PathVariable Long id, @Valid @ModelAttribute Instructor instructor,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "instructors/form";
        }
        
        instructorService.save(instructor);
        redirectAttributes.addFlashAttribute("success", "Instructor updated successfully!");
        
        return "redirect:/instructors";
    }

    @GetMapping("/{id}/delete")
    public String deleteInstructor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        instructorService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Instructor deleted successfully!");
        
        return "redirect:/instructors";
    }
}
