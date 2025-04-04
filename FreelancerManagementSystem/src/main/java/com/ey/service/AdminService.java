package com.ey.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ey.exception.ResourceNotFoundException;
import com.ey.model.Profile;
import com.ey.model.Skill;
import com.ey.model.UserStatus;
import com.ey.repository.ProfileRepository;
import com.ey.repository.SkillRepository;
import com.ey.repository.UserRepository;
import com.ey.repository.UserStatusRepository;
import com.ey.request.SkillRequest;
import com.ey.response.SkillResponse;
import com.ey.response.UserResponse;

import jakarta.transaction.Transactional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ProfileRepository profileRepository;
    
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            String userStatus = userStatusRepository.findByUser_UserId(user.getUserId())
                    .map(status -> status.getStatus().name()) 
                    .orElse("PENDING"); 

            return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                userStatus, 
                user.getCreatedAt(),
                user.getUpdatedAt()
            );
        }).collect(Collectors.toList());
    }
    public String updateUserStatus(Long userId, UserStatus.Status status) {
        Optional<UserStatus> userStatusOptional = userStatusRepository.findByUser_UserId(userId);

        UserStatus userStatus = userStatusOptional.orElseThrow(() ->
                new ResourceNotFoundException("User status not found for user ID: " + userId));

        userStatus.setStatus(status);
        userStatusRepository.save(userStatus);
        return "User status updated to: " + status;
    }

    @Transactional
    public String deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        profileRepository.deleteByUser_UserId(userId);
        userStatusRepository.deleteById(userId);
        userRepository.deleteById(userId);
        return "User deleted successfully";
    }
    
    public SkillResponse addSkill(SkillRequest request) {
        if (skillRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Skill already exists");
        }
        Skill skill = new Skill();
        skill.setName(request.getName());
        skill = skillRepository.save(skill);
        return new SkillResponse(skill.getId(), skill.getName());
    }
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(skill -> new SkillResponse(skill.getId(), skill.getName()))
                .collect(Collectors.toList());
    }
    
    public SkillResponse updateSkill(Long skillId, SkillRequest request) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        skill.setName(request.getName());
        skill = skillRepository.save(skill);
        return new SkillResponse(skill.getId(), skill.getName());
    }
    
    @Transactional
    public String deleteSkill(Long skillId) {
    	Skill skill = skillRepository.findById(skillId)
    	        .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        List<Profile> profilesWithSkill = profileRepository.findAllBySkillsContaining(skill);
        for (Profile profile : profilesWithSkill) {
            profile.getSkills().remove(skill);
        }
        skillRepository.deleteById(skillId);
        return "Skill deleted successfully";
    }
}
