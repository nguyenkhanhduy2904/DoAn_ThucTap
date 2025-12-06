package com.foodapp.backend.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(UserMapper::toDTO).toList();
    }
    public UserDTO getUserByID(Integer userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new IllegalStateException("user with id: "+ userid + " doesnt exist"));
        return UserMapper.toDTO(user);
    }

    public void addUser(User user) {
        System.out.println(user);
        userRepository.save(user);
    }

    public void deleteUser(Integer userid) {
       boolean isUserExist = userRepository.existsById(userid);
       if(!isUserExist){
           throw new IllegalStateException("User with id: "+ userid + " doesnt exist" );
       }
       userRepository.deleteById(userid);
    }


    @Transactional
    public void updateUser(Integer userid,String tenHienThi, String diaChi, String gioiTinh, String sdt) {
        User user = userRepository.findById(userid).orElseThrow(() -> new IllegalStateException("user with id: "+ userid + " doesnt exist"));

        //update TenHienThi
        if(tenHienThi !=null && !tenHienThi.isBlank() && !Objects.equals(user.getTenHienThi(), tenHienThi)){
            user.setTenHienThi(tenHienThi);

        }
        // Update DiaChi
        if (diaChi != null && !diaChi.isBlank()
                && !Objects.equals(user.getDiaChi(), diaChi)) {
            user.setDiaChi(diaChi);
        }

        // Update GioiTinh
        if (gioiTinh != null && !gioiTinh.isBlank()
                && !Objects.equals(user.getGioiTinh(), gioiTinh)) {
            user.setGioiTinh(gioiTinh);
        }

        // Update SDT
        if (sdt != null && !sdt.isBlank()
                && !Objects.equals(user.getSDT(), sdt)) {
            user.setSDT(sdt);
        }


    }


}
