package com.codegym.loverbe.service.role;

import com.codegym.loverbe.model.Role;
import com.codegym.loverbe.model.RoleName;
import com.codegym.loverbe.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService{

    @Autowired
    IRoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
