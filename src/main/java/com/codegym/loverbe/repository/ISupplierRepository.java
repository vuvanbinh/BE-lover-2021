package com.codegym.loverbe.repository;

import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier,Long> {
    @Query(" SELECT c FROM Supplier c ORDER BY c.count DESC")
    List<Supplier> top6();
    Optional<Supplier>findByUser(User user);
    Iterable<Supplier> findByNameContaining(String name);
//    @Query( "SELECT yearOfBirth FROM Supplier WHERE yearOfBirth BETWEEN 1 AND 2")
//    List<Supplier> yearOfBirth1825();
    Iterable<Supplier> findByCity(String city);
    Iterable<Supplier> findBySex(String sex);
    @Query(" SELECT c FROM Supplier c ORDER BY c.count DESC")
    List<Supplier> minView();
}
