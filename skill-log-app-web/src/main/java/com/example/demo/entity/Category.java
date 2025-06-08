package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	/** スキルID */
	private Long skillId;
	
	/** スキル名 */
	private String name;
	
    // デフォルトコンストラクタ（JPA用に必須）
    public Category() {}

    // nameだけ受け取る便利コンストラクタ
    public Category(String name) {
        this.name = name;
    }

	public Long getId() {
		return skillId;
	}

	/**
	 * @return the skillId
	 */
	public Long getSkillId() {https://chatgpt.com/c/682de7e2-f318-800c-bf92-49e77de24a8b
		return skillId;
	}

	/**
	 * @param skillId the skillId to set
	 */
	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		skillId = id;
	}
}
