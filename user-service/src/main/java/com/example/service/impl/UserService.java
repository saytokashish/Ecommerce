package com.example.service.impl;

import com.example.entities.User;
import com.example.dto.UserDTO;
import com.example.repositories.UserRepo;
import com.example.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
    
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(this::toDTO)
                .toList();
    }

    
    public UserDTO getUserById(Long id) throws Exception {
        log.info("Fetching user by id: {}", id);
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.warn("User does not exist with id: {}", id);
            throw new Exception("User does not exist with id: " + id);
        }
        return toDTO(user);
    }

    
    public void createUser(UserDTO userDTO) {
        log.info("Creating user: {}", userDTO);
        User user = toEntity(userDTO);
        user.setId(null);
        userRepo.save(user);
        log.info("User created successfully: {}", user);
    }

    
    public void updateUser(Long id, UserDTO userDTO) throws Exception {
        log.info("Updating user with id: {} and data: {}", id, userDTO);
        User existingUser = userRepo.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(userDTO.getName());
            existingUser.setEmail(userDTO.getEmail());
            userRepo.save(existingUser);
            log.info("User updated successfully: {}", existingUser);
        } else {
            log.warn("User does not exist with id: {}", id);
            throw new Exception("User does not exist with id: " + id);
        }
    }

    
    public void deleteUser(Long id) throws Exception {
        log.info("Deleting user with id: {}", id);
        if (!userRepo.existsById(id)) {
            log.warn("User does not exist with id: {}", id);
            throw new Exception("User does not exist with id: " + id);
        }
        userRepo.deleteById(id);
        log.info("User deleted successfully with id: {}", id);
    }
}
