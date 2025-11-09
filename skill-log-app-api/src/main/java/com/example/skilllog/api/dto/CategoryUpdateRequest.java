// CategoryUpdateRequest.java（更新リクエスト）
package com.example.skilllog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryUpdateRequest {
    @NotBlank
    @Size(max = 64)
    private final String name;

    public CategoryUpdateRequest(String name) { 
    	this.name = name; 
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}