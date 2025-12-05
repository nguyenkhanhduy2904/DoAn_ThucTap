package com.foodapp.backend.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {// config auto chay khi backend chay

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){//auto tao 1 admin user neu Repo.count ==0
        return args -> {
            if (userRepository.count() == 0) { // only add if table is empty
                User defaultUser = new User(
                        "admin",
                        "hashedPassword123", // store hashed password
                        "ADMIN",
                        "0123456789",
                        "123 Main Street",
                        "avatar.png",
                        "Administrator",
                        "Male"
                );
                userRepository.save(defaultUser);
            }
        };


    }
}
