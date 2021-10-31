package com.codegym.loverbe.service.role;

import com.codegym.loverbe.model.Role;
import com.codegym.loverbe.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
