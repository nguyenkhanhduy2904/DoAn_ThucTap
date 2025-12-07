package com.foodapp.backend.login;

import com.foodapp.backend.users.User;
import com.foodapp.backend.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder =  new BCryptPasswordEncoder();
    }

    public boolean checkLogin(String username, String password) {
        User user = userRepository.findByTenDangNhap(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        String hashed = user.getMatKhauHashed(); // lấy hashed từ db

        return passwordEncoder.matches(password, hashed);
    }




}
