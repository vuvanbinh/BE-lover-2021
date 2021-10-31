package com.codegym.loverbe.repository;

import com.codegym.loverbe.model.Role;
import com.codegym.loverbe.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);

}
