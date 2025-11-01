package com.example.skilllog.api.controller;

import java.net.URI;
import java.util.ArrayList;
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

import com.example.service.CategoryService;
import com.example.skilllog.api.dto.CategoryDto;

import jakarta.validation.Valid;

import com.example.entity.Category;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {
    private final CategoryService categoryService;

    // コンストラクタインジェクション
    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * カテゴリ一覧を取得するエンドポイント（GET /api/categories）
     *
     * データベースに登録されているすべてのカテゴリを取得し、
     * HTTPステータス 200 OK と共にレスポンスボディに返します。
     *
     * @return ResponseEntity<カテゴリ一覧>
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> list() {
    	
    	// categoryエンティティを全件取り出し、リスト化
        List<Category> CategoryList = categoryService.findAll();
        // categoryDtoのリストを定義
        List<CategoryDto> CategoryDtoList = new ArrayList<CategoryDto>();
        for (Category c : CategoryList) {
        	CategoryDtoList.add(new CategoryDto(c.getId(), c.getName()));
        }
        // カテゴリ一覧を本文にセットしたHTTP 200 OKレスポンスを返す
        return ResponseEntity.ok(CategoryDtoList);
        
    }

    /**
     * 指定したIDのカテゴリを取得するエンドポイント（GET /api/categories/{id}）
     *
     * 指定されたIDでカテゴリを検索し、存在する場合は200 OKとカテゴリ情報を返します。
     * 存在しない場合は HTTPステータス 404 Not Found を返します。
     *
     * @param id 取得対象カテゴリのID（URLパスから取得）
     * @return ResponseEntity<カテゴリ情報 or 404 Not Found>
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> get(@PathVariable Long id) {
        // Optionalはnullの代わりに「値がある/ない」を表すラッパー
        // categoryOptには見つかったCategoryまたは空（Optional.empty）が入る
        Optional<Category> categoryOpt = categoryService.findById(id);

        if (categoryOpt.isPresent()) { // 見つかった場合
        	// categoryエンティティを取得
            Category category =categoryOpt.get();
            // 本文にCategoryをセットして200 OKを返す
            return ResponseEntity.ok(new CategoryDto(
            		category.getId() // ID
            		, category.getName())); // 名前
        } else {
            // 見つからなかった場合は404 Not Foundを返す
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * カテゴリを新規作成するエンドポイント（POST /api/categories）
     *
     * クライアントから受け取ったカテゴリ情報をデータベースに保存し、
     * HTTPステータス 201 Created を返します。
     * また、レスポンスヘッダーの Location に作成されたリソースのURLを設定し、
     * 保存結果のカテゴリ情報をレスポンスボディに含めます。
     *
     * @param category リクエストボディから受け取るカテゴリ情報
     * @return ResponseEntity<Locationヘッダー + 保存済みカテゴリ情報>
     */
    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        Category saved = categoryService.save(category);
        // 201 Created + Locationヘッダーに作成リソースのURLを設定し、保存結果をボディで返す
        return ResponseEntity
                .created(URI.create("/api/categories/" + saved.getId()))
                .body(saved);
    }

    /**
     * カテゴリを更新するエンドポイント（PUT /api/categories/{id}）
     *
     * 指定されたIDのカテゴリをデータベースから検索し、
     * 存在する場合はリクエストボディの内容で必要な項目だけ更新して保存します。
     * 存在しない場合は HTTPステータス 404 Not Found を返します。
     *
     * @param id 更新対象カテゴリのID（URLパスから取得）
     * @param categoryRequest 更新後のカテゴリ情報（リクエストボディから取得）
     * @return ResponseEntity<更新後のカテゴリ情報 or 404 Not Found>
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @Valid @RequestBody Category categoryRequest) {
        // IDに該当するカテゴリを検索（結果はOptional型で返る）
        Optional<Category> categoryOpt = categoryService.findById(id);

        if (categoryOpt.isPresent()) {
            // カテゴリが存在する場合
            Category category = categoryOpt.get();
            category.setName(categoryRequest.getName()); // 必要な項目だけ更新
            Category updatedCategory = categoryService.save(category); // 更新結果をDBに保存
            CategoryDto categoryDto = new CategoryDto(updatedCategory.getId(), updatedCategory.getName());
            return ResponseEntity.ok(categoryDto); // 200 OK + 更新後のデータを返す
        } else {
            // カテゴリが存在しない場合
            return ResponseEntity.notFound().build(); // 404 Not Found を返す
        }
    }

    /**
     * カテゴリを削除するエンドポイント（DELETE /api/categories/{id}）
     *
     * 指定されたIDのカテゴリを検索し、
     * 存在する場合はデータベースから削除します。
     * 存在しない場合は HTTPステータス 404 Not Found を返します。
     *
     * @param id 削除対象カテゴリのID（URLパスから取得）
     * @return ResponseEntity<削除成功時は204 No Content、未存在なら404 Not Found>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // IDに該当するカテゴリを検索
        Optional<Category> categoryOpt = categoryService.findById(id);

        if (categoryOpt.isPresent()) {
            // カテゴリが存在する場合 → 削除処理
            categoryService.deleteById(id);
            // 204 No Content を返す（削除成功だが、返す内容はなし）
            return ResponseEntity.noContent().build();
        } else {
            // カテゴリが存在しない場合 → 404 Not Found を返す
            return ResponseEntity.notFound().build();
        }
    }

}
