package com.tekarch.UserManagementMS.Services;

import com.tekarch.UserManagementMS.Models.Users;
import com.tekarch.UserManagementMS.Repositories.UserRepository;
import com.tekarch.UserManagementMS.Services.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

  @Override
    public Users createUser(Users user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return userRepository.save(user);
    }


    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public Optional<Users> getUserById(Long userId) {
        return userRepository.findById(userId);
    }


    @Override
    public Optional<Users> updateUser(Long id, Users updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPasswordHash(updatedUser.getPasswordHash());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setTwoFactorEnabled(updatedUser.getTwoFactorEnabled());
            user.setKycStatus(updatedUser.getKycStatus());
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        });
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Users updateKycStatus(Long userId, String kycStatus) {
        return userRepository.findById(userId).map(user -> {
            user.setKycStatus(kycStatus);
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}




