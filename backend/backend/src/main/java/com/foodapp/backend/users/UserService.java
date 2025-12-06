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

    public List<User> getUsers(){
//        return List.of(
//                new User(
//                        1,
//                        "admin1",
//                        "hashed",
//                        "ADMIN",
//                        "0827281099",
//                        "120 yen lang, cao bang",
//                        "url",
//                        "Admin_default",
//                        "nam"
//                )
//        );
        return userRepository.findAll();
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

        if(tenHienThi !=null && tenHienThi.length()> 0 && !Objects.equals(user.getTenHienThi(), tenHienThi)){
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
