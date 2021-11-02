package com.codegym.loverbe.service.user;

import com.codegym.loverbe.model.User;
import com.codegym.loverbe.service.IService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService extends IService<User> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
