package com.ey.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.ey.model.Role;
import com.ey.request.UserRequest;
import com.ey.response.UserResponse;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        UserRequest req = new UserRequest();
        req.setUsername("junitUser");
        req.setPassword("password123");
        req.setEmail("junit@test.com");
        req.setRole(Role.SEEKER);

        UserResponse response = userService.createUser(req);

        assertNotNull(response.getUserId());
        assertEquals("junitUser", response.getUsername());
        assertEquals("junit@test.com", response.getEmail());
        assertEquals("SEEKER", response.getRole().name());
        assertEquals("PENDING", response.getUserStatus());
    }
}

