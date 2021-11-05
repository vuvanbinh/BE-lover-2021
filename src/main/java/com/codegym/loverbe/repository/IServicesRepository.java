package com.codegym.loverbe.repository;

import com.codegym.loverbe.model.Services;
import com.codegym.loverbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServicesRepository extends JpaRepository<Services,Long> {
//    Iterable<Services>findAllByUser(User user);
}
