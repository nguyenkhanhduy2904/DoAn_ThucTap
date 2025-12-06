package com.foodapp.backend.users;

public class UserMapper {
    public static UserDTO toDTO(User user){
        UserDTO dto = new UserDTO(
                user.getTenHienThi(),
                user.getSDT(),
                user.getDiaChi(),
                user.getGioiTinh()

        );
        return dto;
    }

}
