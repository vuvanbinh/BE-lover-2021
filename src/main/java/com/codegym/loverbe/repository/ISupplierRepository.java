package com.codegym.loverbe.repository;

import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier,Long> {
    @Query(" SELECT c FROM Supplier c ORDER BY c.view DESC")
    List<Supplier> top6();

    Optional<Supplier>findByUser(User user);


    @Query(value = "SELECT s FROM Supplier s WHERE s.isConfirm=?1 AND s.isActive=?2")
    Page<Supplier> findAllByConfirmAndActive(Boolean isConfirm,Boolean isActive,Pageable pageable);

    Page<Supplier> findAllByNameContaining(String name,Pageable pageable);

    @Query(value = "SELECT s FROM Supplier s " +
            "WHERE s.name LIKE ?1 " +
            "AND (s.yearOfBirth BETWEEN ?2 AND ?3) AND s.sex=?4 AND s.city=?5 AND s.isConfirm=true AND s.isActive=true")
    Page<Supplier> search(String name, int minYear, int maxYear, String sex, String city,Pageable pageable);





}
