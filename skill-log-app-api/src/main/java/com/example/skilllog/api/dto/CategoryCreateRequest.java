// CategoryCreateRequest.java ・・・作成リクエスト用
package com.example.skilllog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryCreateRequest{
	@NotBlank
    @Size(max = 64)
    private final String name;
	
	public CategoryCreateRequest(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
	    

