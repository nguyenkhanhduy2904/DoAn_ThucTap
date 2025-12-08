package com.foodapp.backend.login;

import com.foodapp.backend.Response.APIResponse;
import com.foodapp.backend.users.User;
import com.foodapp.backend.users.UserDTO;
import com.foodapp.backend.users.UserMapper;
import com.foodapp.backend.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/login")
public class LoginController {


    private final LoginService loginService;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(LoginService loginService, UserRepository userRepository) {
        this.loginService = loginService;
        this.userRepository = userRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<APIResponse<UserDTO>> login(@RequestBody LoginRequest loginReq) {

        try {
            boolean ok = loginService.checkLogin(loginReq.getTenDangNhap(),
                    loginReq.getMatKhauRaw());

            if (ok) {
                User returnUser = userRepository.findByTenDangNhap(loginReq.getTenDangNhap()).orElseThrow(()-> new IllegalStateException("ko tim dc acc nay"));
                UserDTO dto = UserMapper.toDTO(returnUser);
                return ResponseEntity.ok(

                        new APIResponse<>("success", 200, "Login success", dto)
                );
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new APIResponse<>("error", 401, "Wrong info", null));
            }

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse<>("error", 404, e.getMessage(), null));
        }
    }

}
