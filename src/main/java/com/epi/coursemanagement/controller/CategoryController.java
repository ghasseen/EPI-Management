package com.epi.coursemanagement.controller;

import com.epi.coursemanagement.model.Category;
import com.epi.coursemanagement.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model, @RequestParam(defaultValue = "0") int page, 
                                @RequestParam(defaultValue = "5") int size) {
        Page<Category> categoryPage = categoryService.findAll(PageRequest.of(page, size));
        
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("totalItems", categoryPage.getTotalElements());
        
        return "categories/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/form";
    }

    @PostMapping
    public String createCategory(@Valid @ModelAttribute Category category, BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "categories/form";
        }
        
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("success", "Category created successfully!");
        
        return "redirect:/categories";
    }

    @GetMapping("/{id}")
    public String viewCategory(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categories/view";
        } else {
            return "redirect:/categories";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categories/form";
        } else {
            return "redirect:/categories";
        }
    }

    @PostMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @Valid @ModelAttribute Category category,
                               BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "categories/form";
        }
        
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("success", "Category updated successfully!");
        
        return "redirect:/categories";
    }

    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Category deleted successfully!");
        
        return "redirect:/categories";
    }
}
