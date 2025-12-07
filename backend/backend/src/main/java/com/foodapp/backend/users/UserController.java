package com.foodapp.backend.users;

import com.foodapp.backend.Response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping
//    public List<UserDTO> getAllUsers(){
//        return userService.getAllUsers();
//    }

    @GetMapping
    public ResponseEntity<APIResponse<List<UserDTO>>> getAllUser() {
        List<UserDTO> allDTO = userService.getAllUsers();

        APIResponse<List<UserDTO>> response = new APIResponse<>(
                "success",
                200,
                "Fetch success",
                allDTO
        );

        return ResponseEntity.ok(response);
    }




//    @GetMapping(path = "{userid}")
//    public UserDTO getUserByID(@PathVariable("userid") Integer userid){
//        return userService.getUserByID(userid);
//
//    }

    @GetMapping(path = "{userid}")
    public ResponseEntity<APIResponse<UserDTO>> getUserByID(@PathVariable("userid") Integer userid){
        try {
            UserDTO userDTO = userService.getUserByID(userid);
            APIResponse<UserDTO> response = new APIResponse<>("success", 200, "User found", userDTO);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            APIResponse<UserDTO> response = new APIResponse<>("error", 404, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


//    @PostMapping
//    public void addUser(@RequestBody User user){
//        userService.addUser(user);
//    }

    @PostMapping
    public ResponseEntity<APIResponse<UserDTO>> addUser(@RequestBody User user) {
        try {
            UserDTO createdUser = userService.addUser(user);

            APIResponse<UserDTO> response = new APIResponse<>(
                    "success",
                    200,
                    "User created successfully",
                    createdUser
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalStateException e) {
            APIResponse<UserDTO> response = new APIResponse<>(
                    "error",
                    400,
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }




//    @DeleteMapping(path = "{userid}")
//    public void deleteUser(@PathVariable("userid") Integer userid){
//        userService.deleteUser(userid);
//    }

    @DeleteMapping(path = "{userid}")
    public ResponseEntity<APIResponse<Void>> deleteUser(@PathVariable("userid") Integer userid){
        try {
            userService.deleteUser(userid);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    200,
                    "User deleted successfully",
                    null
            );
            return ResponseEntity.ok(response);
        }
        catch (IllegalStateException e) {
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    404,
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }




//    @PutMapping(path = "{userid}")
//    public void updateUser(@PathVariable("userid") Integer userid,
//                           @RequestParam(required = false) String TenHienThi,
//                           @RequestParam(required = false) String DiaChi,
//                           @RequestParam(required = false) String GioiTinh,
//                           @RequestParam(required = false) String SDT) {
//        userService.updateUser(userid ,TenHienThi, DiaChi, GioiTinh, SDT);
//
//    }

    @PutMapping(path = "{userid}")
    public ResponseEntity<APIResponse<Void>> updateUser(
            @PathVariable("userid") Integer userid,
            @RequestBody UserDTO userDTO) {

        try {
            userService.updateUser(userid, userDTO);

            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    200,
                    "Update success",
                    null
            );

            return ResponseEntity.ok(response);
        }
        catch (IllegalStateException e) {

            APIResponse<Void> response = new APIResponse<>(
                    "Update failed",
                    404,
                    e.getMessage(),
                    null
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }






}
