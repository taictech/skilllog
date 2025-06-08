package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/skillLog")
public class CategoryController {
    private final CategoryService slillLogService;

    // コンストラクタインジェクション
    public CategoryController(CategoryService SlillLogService) {
        this.slillLogService = SlillLogService;
    }

    // 全件取得 GET /categories
    @GetMapping
    public List<Category> getAll() {
        return slillLogService.findAll();
    }

    // ID指定で1件取得 GET /categories/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Optional<Category> category = slillLogService.findById(id);
        return category.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    // 新規作成 POST /categories
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        Category saved = slillLogService.save(category);
        return ResponseEntity.ok(saved);
    }

    // 更新 PUT /categories/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        if (!slillLogService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        category.setId(id); // skillIdをセット
        Category updated = slillLogService.save(category);
        return ResponseEntity.ok(updated);
    }

    // 削除 DELETE /categories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!slillLogService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        slillLogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
