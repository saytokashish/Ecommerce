package com.example.service;

import com.example.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id) throws Exception;
    void createUser(UserDTO userDTO);
    void updateUser(Long id, UserDTO userDTO) throws Exception;
    void deleteUser(Long id) throws Exception;
}
