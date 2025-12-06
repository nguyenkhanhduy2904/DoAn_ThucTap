package com.foodapp.backend.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder; //hashed lib

@Configuration
public class UserConfig {// config auto chay khi backend chay

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){//auto tao 1 admin user neu Repo.count ==0
        return args -> {
            if (userRepository.count() == 0) {// only add if table is empty
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                User defaultUser = new User(
                        "admin",
                        passwordEncoder.encode("001"),
                        "ADMIN",
                        "0123456789",
                        "123 Main Street",

                        "Administrator",
                        "Male",
                        "ACTIVE"
                );

                userRepository.save(defaultUser);
            }
        };


    }
}
