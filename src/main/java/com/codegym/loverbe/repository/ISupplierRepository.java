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
    @Query(" SELECT c FROM Supplier c ORDER BY c.count DESC")
    List<Supplier> top6();

    Optional<Supplier>findByUser(User user);

    @Query(value = "SELECT s FROM Supplier s WHERE s.isConfirm=?1")
    Page<Supplier> findAllByConfirm(Boolean isConfirm,Pageable pageable);

    @Query(value = "SELECT s FROM Supplier s WHERE s.sex='nam'")
    Page<Supplier> findAllBySex(String sex,Pageable pageable);

    @Query(value = "SELECT s FROM Supplier s WHERE s.sex='nu'")
    Page<Supplier> findUserBySex(String sex,Pageable pageable);
}
