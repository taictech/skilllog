package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Category;
import com.example.repository.CategoryRepository;



@Service
@Transactional(readOnly = true)
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
    
    @Transactional
    // 新規作成 or 更新（idの有無で判断）
    public Category save(Category category) {
        return categpryRepository.save(category);
    }
    
    @Transactional
    // IDで削除
    public void deleteById(Long id) {
        categpryRepository.deleteById(id);
    }
}
