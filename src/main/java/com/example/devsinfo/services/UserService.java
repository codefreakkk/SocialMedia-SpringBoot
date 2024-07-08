package com.example.devsinfo.services;

import com.example.devsinfo.DTO.LoginDTO;
import com.example.devsinfo.DTO.UserDTO;
import com.example.devsinfo.DTO.interfaces.IUserService;
import com.example.devsinfo.exceptions.DuplicateUserException;
import com.example.devsinfo.models.User;
import com.example.devsinfo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(int userId) {
        return userDao.findById(userId);
    }

    @Override
    public void signup(UserDTO userDTO) throws DuplicateUserException {
        User currentUser = userDao.findByEmail(userDTO.getEmail());
        if (currentUser != null) {
            throw new DuplicateUserException("Email already in use");
        }
        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .gender(userDTO.getGender())
                .bio(userDTO.getBio())
                .techStack(userDTO.getTechStack())
                .build();

        userDao.save(user);
    }

    @Override
    public User loginUser(LoginDTO loginDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        return userDao.findByEmail(loginDTO.getEmail());
    }

}
