package com.foodapp.backend.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(UserMapper::toDTO).toList();
//        List<User> lsUser = userRepository.findAll();
//        List<UserDTO> resultList= new ArrayList<>();
//        for(User user : lsUser){
//            UserDTO dto = UserMapper.toDTO(user);
//            resultList.add(dto);
//        }
//        return resultList;
    }
    public UserDTO getUserByID(Integer userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new IllegalStateException("user with id: "+ userid + " doesnt exist"));
        return UserMapper.toDTO(user);
    }

    public UserDTO addUser(User user) {

        // 1. validate unique username
        if (userRepository.existsByTenDangNhap(user.getTenDangNhap())) {
            throw new IllegalStateException("TenDangNhap already exist");
        }

        // 2. hash password
        String hashed = passwordEncoder.encode(user.getMatKhauHashed());
        user.setMatKhauHashed(hashed);

        // 3. save
        User saved = userRepository.save(user);

        // 4. convert to DTO
        return UserMapper.toDTO(saved);
    }


    public void deleteUser(Integer userid) {
       boolean isUserExist = userRepository.existsById(userid);
       if(!isUserExist){
           throw new IllegalStateException("User with id: "+ userid + " doesnt exist" );
       }
       userRepository.deleteById(userid);
    }


//    @Transactional
//    public void updateUser(Integer userid,String tenHienThi, String diaChi, String gioiTinh, String sdt) {
//        User user = userRepository.findById(userid).orElseThrow(() -> new IllegalStateException("user with id: "+ userid + " doesnt exist"));
//
//        //update TenHienThi
//        if(tenHienThi !=null && !tenHienThi.isBlank() && !Objects.equals(user.getTenHienThi(), tenHienThi)){
//            user.setTenHienThi(tenHienThi);
//
//        }
//        // Update DiaChi
//        if (diaChi != null && !diaChi.isBlank()
//                && !Objects.equals(user.getDiaChi(), diaChi)) {
//            user.setDiaChi(diaChi);
//        }
//
//        // Update GioiTinh
//        if (gioiTinh != null && !gioiTinh.isBlank()
//                && !Objects.equals(user.getGioiTinh(), gioiTinh)) {
//            user.setGioiTinh(gioiTinh);
//        }
//
//        // Update SDT
//        if (sdt != null && !sdt.isBlank()
//                && !Objects.equals(user.getSdt(), sdt)) {
//            user.setSdt(sdt);
//        }
//
//
//    }

    public void updateUser(Integer userid, UserDTO newDataDTO){
        User user = userRepository.findById(userid).orElseThrow(()-> new IllegalStateException("User with id: "+ userid + " doesnt exist" ));

        if(newDataDTO.getTenHienThi() !=null && !newDataDTO.getTenHienThi().isBlank() && !Objects.equals(user.getTenHienThi(), newDataDTO.getTenHienThi()) ){
            user.setTenHienThi(newDataDTO.getTenHienThi());
        }
        if(newDataDTO.getDiaChi()!=null && !newDataDTO.getDiaChi().isBlank() && !Objects.equals(user.getDiaChi(), newDataDTO.getDiaChi())){
            user.setDiaChi(newDataDTO.getDiaChi());
        }
        if(newDataDTO.getSdt()!=null && !newDataDTO.getSdt().isBlank() && !Objects.equals(user.getSdt(), newDataDTO.getSdt())){
            user.setSdt(newDataDTO.getSdt());
        }
        if(newDataDTO.getGioiTinh()!=null && !newDataDTO.getGioiTinh().isBlank() && !Objects.equals(user.getGioiTinh(), newDataDTO.getGioiTinh())){
            user.setGioiTinh(newDataDTO.getGioiTinh());
        }
        if(newDataDTO.getEmail()!=null && !newDataDTO.getEmail().isBlank() && !Objects.equals(user.getEmail(), newDataDTO.getEmail())){
            user.setEmail(newDataDTO.getEmail());
        }

        userRepository.save(user);




    }




}
