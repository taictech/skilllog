package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.SkillLog;
import com.example.demo.service.SkillLogService;

@Controller
@RequestMapping("/api/skilllog")
public class SkillLogController {
	
    private final SkillLogService skillLogService;

    public SkillLogController(SkillLogService skillLogService) {
        this.skillLogService = skillLogService;
    }

    @GetMapping
    public List<SkillLog> getAll() {
        return skillLogService.findAll();
    }
    
    @GetMapping("/category/{skillId}")
    public List<SkillLog> getByCategory(@PathVariable Long skillId) {
        return skillLogService.findByCategorySkillId(skillId);
    }
    
    @GetMapping("/category/{skillId}/total-time")
    public Map<String, Object> getTotalTimeByCategory(@PathVariable Long skillId) {
        int totalMinutes = skillLogService.getTotalMinutesByCategoryId(skillId);

        Map<String, Object> response = new HashMap<>();
        response.put("categoryId", skillId);
        response.put("totalMinutes", totalMinutes);
        return response;
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<SkillLog> update(@PathVariable Long id, @RequestBody SkillLog newLog) {
        Optional<SkillLog> existing = skillLogService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SkillLog log = existing.get();
        log.setDescription(newLog.getDescription());
        log.setMinutes(newLog.getMinutes());
        log.setDate(newLog.getDate());
        log.setCategory(newLog.getCategory());

        SkillLog updated = skillLogService.save(log);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<SkillLog> create(@RequestBody SkillLog skillLog) {
        SkillLog saved = skillLogService.save(skillLog);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillLog> getById(@PathVariable Long id) {
        Optional<SkillLog> skillLog = skillLogService.findById(id);
        return skillLog.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (skillLogService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        skillLogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
