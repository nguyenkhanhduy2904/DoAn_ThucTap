package com.foodapp.backend.users;

public class UserMapper {
    public static UserDTO toDTO(User user){
        UserDTO dto = new UserDTO(
                user.getId(),
                user.getTenHienThi(),
                user.getSdt(),
                user.getRole(),
                user.getDiaChi(),
                user.getGioiTinh(),
                user.getTrangThai()
        );
        return dto;
    }

}
