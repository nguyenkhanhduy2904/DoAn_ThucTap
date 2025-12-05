package com.foodapp.backend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DangNhap {
	private Integer flag =0;
    private Integer role_flag =0;
    @PostMapping(value="/login",produces = "application/json; charset=UTF-8") 
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
            response.put("message", "Sai tài khoản hoặc sai mật khẩu");
        }

        return response;
    }
}
