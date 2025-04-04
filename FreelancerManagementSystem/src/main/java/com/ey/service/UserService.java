package com.ey.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ey.exception.UserNotFoundException;
import com.ey.model.Role;
import com.ey.model.User;
import com.ey.model.UserStatus;
import com.ey.repository.UserRepository;
import com.ey.repository.UserStatusRepository;
import com.ey.request.UserRequest;
import com.ey.response.UserResponse;
import com.ey.response.UserStatusResponse;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;  
    @Autowired
    private ProfileService profileService;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        // Create user entity
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword())); 
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());

        // Save user to database
        User savedUser = userRepository.save(user);

        // Create and save user status with PENDING state
        UserStatus userStatus = new UserStatus();
        userStatus.setUser(savedUser);
        userStatus.setStatus(UserStatus.Status.PENDING);
        userStatusRepository.save(userStatus);
        
        if (userRequest.getRole() == Role.FREELANCER) {
            profileService.createProfileForFreelancer(savedUser);
        }

        // Return user response
        return new UserResponse(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole(),
                "PENDING",  // Set status as PENDING
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        // Retrieve the user's status from the UserStatus table
        UserStatus userStatus = userStatusRepository.findByUser_UserId(id)
                .orElseThrow(() -> new UserNotFoundException("User status not found for ID: " + id));

        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                userStatus.getStatus().name(),  // Include the user status
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
    
    public UserStatusResponse getUserStatus(Long userId) {
        // Check if user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        // Fetch user status
        Optional<UserStatus> userStatusOptional = userStatusRepository.findByUser_UserId(userId);
        if (userStatusOptional.isEmpty()) {
            throw new RuntimeException("User status not found for ID: " + userId);
        }

        UserStatus userStatus = userStatusOptional.get();

        // Return response DTO
        return new UserStatusResponse(userStatus.getUser().getUserId(), userStatus.getStatus().name());
    }
}
