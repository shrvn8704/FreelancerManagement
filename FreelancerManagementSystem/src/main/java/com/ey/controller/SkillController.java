package com.ey.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.repository.SkillRepository;
import com.ey.response.SkillResponse;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        List<SkillResponse> skillResponses = skillRepository.findAll().stream()
                .map(skill -> new SkillResponse(skill.getId(), skill.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(skillResponses);
    }
}
