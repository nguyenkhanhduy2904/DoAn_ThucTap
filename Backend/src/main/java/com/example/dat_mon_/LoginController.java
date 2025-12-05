package com.example.dat_mon_;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController 
public class LoginController {
    private Integer flag =0;
    private Integer role_flag =0;
    @PostMapping("/login") 
    public Map<String, String> login(
            @RequestParam String username, 
            @RequestParam String password,
            @RequestParam String role
    ) {
        Map<String, String> response = new HashMap<>();
        if ("admin".equals(username) && "123".equals(password)) {
        		if("admin".equals(role)){
            		flag=1;
            		role_flag=1;
        		}
        }
        if (flag==1 && role_flag==1) {
            response.put("status", "success");
            response.put("message", "Đăng nhập thành công ");
            flag = 0;
            role_flag=0;
          }
        else if (role_flag==0){
        	response.put("status", "failed");
            response.put("message", "Sai vai trò");
        }
        else {
            response.put("status", "failed");
            response.put("message", "Sai tài khoản hoặc mật khẩu");
        }

        return response;
    }
}
