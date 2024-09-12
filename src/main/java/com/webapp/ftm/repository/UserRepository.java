package com.webapp.ftm.repository;

import com.webapp.ftm.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    public UserEntity findByEmail(String email);
    public boolean existsByEmail(String email);
    public UserEntity findByRefreshTokenAndEmail(String refreshToken, String email);
}
