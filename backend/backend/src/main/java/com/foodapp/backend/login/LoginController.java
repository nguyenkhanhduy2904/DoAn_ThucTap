package com.foodapp.backend.login;

import com.foodapp.backend.Response.APIResponse;
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

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping("/login")
    public ResponseEntity<APIResponse<Void>> login(@RequestBody LoginRequest loginReq) {

        try {
            boolean ok = loginService.checkLogin(loginReq.getTenDangNhap(),
                    loginReq.getMatKhauRaw());

            if (ok) {
                return ResponseEntity.ok(
                        new APIResponse<>("success", 200, "Login success", null)
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
