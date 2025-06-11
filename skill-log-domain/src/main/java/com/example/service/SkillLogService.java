package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.SkillLog;
import com.example.repository.SkillLogRepository;

@Service
public class SkillLogService {
	
    private final SkillLogRepository skillLogRepository;

    public SkillLogService(SkillLogRepository skillLogRepository) {
        this.skillLogRepository = skillLogRepository;
    }

    public List<SkillLog> findAll() {
        return skillLogRepository.findAll();
    }

    public Optional<SkillLog> findById(Long id) {
        return skillLogRepository.findById(id);
    }
    
    public List<SkillLog> findByCategorySkillId(Long skillId) {
        return skillLogRepository.findByCategorySkillId(skillId);
    }
    
    public List<SkillLog> findByCategoryId(Long categoryId) {
        return skillLogRepository.findByCategoryIdOrderByDateDesc(categoryId);
    }

    public int getTotalMinutesByCategoryId(Long skillId) {
        Integer total = skillLogRepository.getTotalMinutesByCategoryId(skillId);
        return total != null ? total : 0;
    }

    public SkillLog save(SkillLog skillLog) {
        return skillLogRepository.save(skillLog);
    }

    public void deleteById(Long id) {
        skillLogRepository.deleteById(id);
    }
}
