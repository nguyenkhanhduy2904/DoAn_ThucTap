package com.foodapp.backend.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "{userid}")
    public UserDTO getUserByID(@PathVariable("userid") Integer userid){
        return userService.getUserByID(userid);

    }

    @PostMapping
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @DeleteMapping(path = "{userid}")
    public void deleteUser(@PathVariable("userid") Integer userid){
        userService.deleteUser(userid);
    }

    @PutMapping(path = "{userid}")
    public void updateUser(@PathVariable("userid") Integer userid,
                           @RequestParam(required = false) String TenHienThi,
                           @RequestParam(required = false) String DiaChi,
                           @RequestParam(required = false) String GioiTinh,
                           @RequestParam(required = false) String SDT) {
        userService.updateUser(userid ,TenHienThi, DiaChi, GioiTinh, SDT);

    }



}
