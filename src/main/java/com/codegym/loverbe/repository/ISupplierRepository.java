package com.codegym.loverbe.repository;

import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier,Long> {
    @Query(" SELECT c FROM Supplier c ORDER BY c.count DESC")
    List<Supplier> top6();

    Optional<Supplier> findByUser(User user);

    Iterable<Supplier> findByName(String name);

    @Query( value = "SELECT * FROM suppliers WHERE year_Of_Birth BETWEEN 18 AND 23",nativeQuery = true)
    Page<Supplier> yearOfBirth1823(Pageable pageable);

    @Query( value = "SELECT * FROM suppliers WHERE year_Of_Birth BETWEEN 23 AND 27",nativeQuery = true)
    List<Supplier> yearOfBirth2327();

    @Query( value = "SELECT * FROM suppliers WHERE year_Of_Birth BETWEEN 27 AND 31",nativeQuery = true)
    List<Supplier> yearOfBirth2731();

    Iterable<Supplier> findByCity(String city);

    Iterable<Supplier> findBySex(String sex);


}