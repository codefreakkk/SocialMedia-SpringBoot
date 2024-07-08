package com.example.devsinfo.DTO.interfaces;

import com.example.devsinfo.DTO.LoginDTO;
import com.example.devsinfo.DTO.UserDTO;
import com.example.devsinfo.exceptions.DuplicateUserException;
import com.example.devsinfo.models.User;

public interface IUserService {
    public User getUserById(int userId);
    public void signup(UserDTO userDTO) throws DuplicateUserException;
    public User loginUser(LoginDTO loginDTO);
}
