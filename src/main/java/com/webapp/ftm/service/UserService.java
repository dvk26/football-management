package com.webapp.ftm.service;

import com.webapp.ftm.dto.UserDTO;
import com.webapp.ftm.model.UserEntity;

public interface UserService {
     public UserEntity findByRefreshTokenAndEmail(String refreshToken ,String email);

     public UserEntity findByEmail(String email);
     public void updateUserToken(String refreshToken, String email);

     public void handleCreateUser(UserDTO userDTO);
}
