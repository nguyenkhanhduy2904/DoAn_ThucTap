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
        if(newDataDTO.getTrangThai()!=null && !newDataDTO.getTrangThai().isBlank() && !Objects.equals(user.getTrangThai(), newDataDTO.getTrangThai())){
            user.setTrangThai(newDataDTO.getTrangThai());
        }

        userRepository.save(user);

    }


    public void changePassword(String oldPassword, String newPassword, int userid){
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new IllegalStateException("User with id: " + userid + " doesn't exist"));

        // Check if the old password matches the stored hash
        if (!passwordEncoder.matches(oldPassword, user.getMatKhauHashed())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // Optional: prevent reusing the same password
        if (passwordEncoder.matches(newPassword, user.getMatKhauHashed())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }

        // Encode the new password and save
        user.setMatKhauHashed(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    public void resetPassword(String userName, String sdt, String email, String newPassword) {
        User user = userRepository.findByTenDangNhap(userName)
                .orElseThrow(() -> new IllegalStateException("User with username " + userName + " not found"));

        // Verify phone and email
        if (!user.getSdt().equals(sdt)||!user.getEmail().equals(email) ) {
            throw new IllegalArgumentException("Info does not match");
        }

        // Hash and update new password
        String hashed = passwordEncoder.encode(newPassword);
        user.setMatKhauHashed(hashed);

        userRepository.save(user);
    }

}
