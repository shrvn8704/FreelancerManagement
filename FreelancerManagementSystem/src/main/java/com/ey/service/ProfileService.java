package com.ey.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ey.model.Profile;
import com.ey.model.Skill;
import com.ey.model.User;
import com.ey.repository.ProfileRepository;
import com.ey.repository.SkillRepository;
import com.ey.repository.UserRepository;
import com.ey.request.ProfileRequest;
import com.ey.response.ProfileResponse;

import jakarta.transaction.Transactional;

@Service
public class ProfileService {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";
    

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SkillRepository skillRepository;

    public ProfileResponse createProfile(ProfileRequest request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        Set<Skill> skills = skillRepository.findAllById(request.getSkillIds()).stream().collect(Collectors.toSet());

        Profile profile = new Profile(userOpt.get(), request.getName(), request.getBio(), request.getPortfolioLink(), null, request.getPricing(), skills);
        Profile savedProfile = profileRepository.save(profile);

        return new ProfileResponse(savedProfile);
    }

    public ProfileResponse uploadPortfolio(Long userId, MultipartFile file) throws IOException {
        Optional<Profile> profileOpt = profileRepository.findByUserUserId(userId);
        if (profileOpt.isEmpty()) {
            throw new RuntimeException("Profile not found");
        }
        System.out.println("THIS IS THE PATH "+UPLOAD_DIR);
        Profile profile = profileOpt.get();
        Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
        file.transferTo(new File(filePath.toString()));

        profile.setPortfolioFilePath(filePath.toString());
        profileRepository.save(profile);

        return new ProfileResponse(profile);
    }

    public List<ProfileResponse> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream().map(ProfileResponse::new).collect(Collectors.toList());
    }

    public ProfileResponse getProfileByUserId(Long userId) {
        Optional<Profile> profileOpt = profileRepository.findByUserUserId(userId);
        if (profileOpt.isEmpty()) {
            throw new RuntimeException("Profile not found for user ID: " + userId);
        }
        return new ProfileResponse(profileOpt.get());
    }

    public ProfileResponse getProfileById(Long profileId) {
        Optional<Profile> profileOpt = profileRepository.findById(profileId);
        if (profileOpt.isEmpty()) {
            throw new RuntimeException("Profile not found with ID: " + profileId);
        }
        return new ProfileResponse(profileOpt.get());
    }

    public ProfileResponse updateProfile(Long profileId, ProfileRequest request) {
        Optional<Profile> profileOpt = profileRepository.findById(profileId);
        if (profileOpt.isEmpty()) {
            throw new RuntimeException("Profile not found");
        }

        Profile profile = profileOpt.get();
        profile.setName(request.getName());
        profile.setBio(request.getBio());
        profile.setPortfolioLink(request.getPortfolioLink());
        profile.setPricing(request.getPricing());
        profile.setUpdatedAt(LocalDateTime.now());
        
        Set<Skill> skills = skillRepository.findAllById(request.getSkillIds()).stream().collect(Collectors.toSet());
        profile.setSkills(skills);

        Profile updatedProfile = profileRepository.save(profile);
        return new ProfileResponse(updatedProfile);
    }
    
    @Transactional
    public void createProfileForFreelancer(User user) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setName(user.getUsername()); // Set name as username initially
        profile.setBio(""); // Default empty bio
        profile.setPortfolioLink(null);
        profile.setPortfolioFilePath(null);
        profile.setPricing(0.0); // Default pricing
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());
        
        profileRepository.save(profile);
        System.out.println("✅ Profile created for Freelancer: " + user.getUsername());
    }
}