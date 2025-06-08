package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.SkillLog;

public interface SkillLogRepository extends JpaRepository<SkillLog, Long> {
    // あとで必要になったらクエリメソッドも追加できます
	
	@Query("SELECT SUM(s.minutes) FROM SkillLog s WHERE s.category.skillId = :skillId")
	Integer getTotalMinutesByCategoryId(@Param("skillId") Long skillId);
	
	// SkillLogRepository.java
	List<SkillLog> findByCategorySkillId(Long skillId);
	
	List<SkillLog> findByCategoryIdOrderByDateDesc(Long categoryId);


}
