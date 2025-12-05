package com.foodapp.backend.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
//        return List.of(
//                new User(
//                        1,
//                        "admin1",
//                        "hashed",
//                        "ADMIN",
//                        "0827281099",
//                        "120 yen lang, cao bang",
//                        "url",
//                        "Admin_default",
//                        "nam"
//                )
//        );
        return userRepository.findAll();
    }

    public void addUser(User user) {
        System.out.println(user);

    }
}
