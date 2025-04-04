package com.ey;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ey.model.Role;
import com.ey.model.User;
import com.ey.model.UserStatus;
import com.ey.repository.UserRepository;
import com.ey.repository.UserStatusRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder,UserStatusRepository userStatusRepository) {
        return args -> {
            Optional<User> existingAdmin = userRepository.findByUsername("admin");

            if (existingAdmin.isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);

                User savedAdmin = userRepository.save(admin);
                UserStatus adminStatus = new UserStatus();
                adminStatus.setUser(savedAdmin);
                adminStatus.setStatus(UserStatus.Status.ACCEPTED);
                userStatusRepository.save(adminStatus);
                System.out.println("Admin user created: username=admin, password=admin123");
            }
        };
    }
}
