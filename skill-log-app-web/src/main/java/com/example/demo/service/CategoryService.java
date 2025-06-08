package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categpryRepository;

    // コンストラクタインジェクション
    public CategoryService(CategoryRepository categoryRepository) {
        this.categpryRepository = categoryRepository;
    }

    // 全件取得
    public List<Category> findAll() {
        return categpryRepository.findAll();
    }

    // IDで1件取得
    public Optional<Category> findById(Long id) {
        return categpryRepository.findById(id);
    }

    // 新規作成 or 更新（idの有無で判断）
    public Category save(Category category) {
        return categpryRepository.save(category);
    }

    // IDで削除
    public void deleteById(Long id) {
        categpryRepository.deleteById(id);
    }
}
