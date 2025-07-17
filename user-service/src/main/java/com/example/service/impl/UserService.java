package com.example.service.impl;

import com.example.entities.User;
import com.example.dto.UserDTO;
import com.example.repositories.UserRepo;
import com.example.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(this::toDTO)
                .toList();
    }

    
    public UserDTO getUserById(Long id) throws Exception {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            throw new Exception("User does not exist with id: " + id);
        }
        return toDTO(user);
    }

    
    public void createUser(UserDTO userDTO) {
        User user = toEntity(userDTO);
        user.setId(null);
        userRepo.save(user);
    }

    
    public void updateUser(Long id, UserDTO userDTO) throws Exception {
        User existingUser = userRepo.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(userDTO.getName());
            existingUser.setEmail(userDTO.getEmail());
            userRepo.save(existingUser);
        } else {
            throw new Exception("User does not exist with id: " + id);
        }
    }

    
    public void deleteUser(Long id) throws Exception {
        if (!userRepo.existsById(id)) {
            throw new Exception("User does not exist with id: " + id);
        }
        userRepo.deleteById(id);
    }
}
