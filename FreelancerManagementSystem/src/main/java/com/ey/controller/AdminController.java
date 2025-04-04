package com.ey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.model.UserStatus;
import com.ey.request.SkillRequest;
import com.ey.request.UserStatusRequest;
import com.ey.response.SkillResponse;
import com.ey.response.UserResponse;
import com.ey.response.UserStatusResponse;
import com.ey.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
    @PutMapping("/user/{userId}/status")
    public ResponseEntity<UserStatusResponse> updateUserStatus(@PathVariable Long userId, 
                                                               @RequestBody UserStatusRequest userStatusRequest) {
        UserStatus.Status userStatus = userStatusRequest.getStatus();
        adminService.updateUserStatus(userId, userStatus); 
        
        return ResponseEntity.ok(new UserStatusResponse(userId, userStatus.name()));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.deleteUser(userId));
    }

    @PostMapping("/skills")
    public ResponseEntity<SkillResponse> addSkill(@RequestBody SkillRequest request) {
        return ResponseEntity.ok(adminService.addSkill(request));
    }


    @GetMapping("/skills")
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        return ResponseEntity.ok(adminService.getAllSkills());
    }


    @PutMapping("/skills/{id}")
    public ResponseEntity<SkillResponse> updateSkill(@PathVariable Long id, @RequestBody SkillRequest request) {
        return ResponseEntity.ok(adminService.updateSkill(id, request));
    }


    @DeleteMapping("/skills/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteSkill(id));
    }
    
}
