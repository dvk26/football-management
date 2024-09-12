package com.webapp.ftm.service.impl;

import com.webapp.ftm.dto.UserDTO;
import com.webapp.ftm.exception.custom.EmailExistsException;
import com.webapp.ftm.model.UserEntity;
import com.webapp.ftm.repository.RoleRepository;
import com.webapp.ftm.repository.UserRepository;
import com.webapp.ftm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserEntity findByRefreshTokenAndEmail(String refreshToken, String email) {
        return userRepository.findByRefreshTokenAndEmail(refreshToken, email);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateUserToken(String refreshToken, String email) {
        UserEntity userEntity= userRepository.findByEmail(email);
        userEntity.setRefreshToken(refreshToken);
        userRepository.save(userEntity);
    }

    @Override
    public void handleCreateUser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail()))
            throw new EmailExistsException("Loi ko tim thay emaik");
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity userEntity= modelMapper.map(userDTO,UserEntity.class);

        if(userDTO.getRole()!=null)
            userEntity.setRole(roleRepository.findById(userDTO.getRole().getId()).get());
        userRepository.save(userEntity);
    }
}
