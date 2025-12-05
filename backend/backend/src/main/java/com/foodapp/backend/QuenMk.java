package com.foodapp.backend;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
public class QuenMk {
	@PostMapping("/forgot")
    public Map<String, String> resetPassword(@RequestParam("sdt") String phone) {
		Map<String, String> response = new HashMap<>();
		if ("0987654321".equals(phone)) {
            response.put("status", "success");
            response.put("message", "Mật khẩu đã reset về 123 ");
        } else {
            response.put("status", "error");
            response.put("message", "Số điện thoại không tồn tại");
        }
		return response;
	}
}
