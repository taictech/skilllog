package com.example.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Category;
import com.example.service.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService service) {
        this.categoryService = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/form";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid Category category, BindingResult result) {
        if (result.hasErrors()) return "category/form";
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
    	Optional<Category> categoryOpt = categoryService.findById(id);
    	if (categoryOpt.isEmpty()) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "指定されたカテゴリが見つかりませんでした");
    	}
    	model.addAttribute("category", categoryOpt.get());

    	return "category/form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute @Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "category/form";
        }
		// パス変数idをエンティティにセット（セキュリティやバインド対策）
		category.setId(id);
		categoryService.save(category);
		return "redirect:/categories";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }
}

