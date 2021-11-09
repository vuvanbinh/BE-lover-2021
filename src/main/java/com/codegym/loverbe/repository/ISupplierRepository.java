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

    @Query(value = "select s from Supplier s where s.sex='nữ' and s.isActive=true and s.isConfirm=true ")
    List<Supplier> find12SupByFemale();

    @Query(value = "select s from Supplier s where s.sex='nam' and s.isActive=true and s.isConfirm=true ")
    List<Supplier> find12SupByMale();

    @Query(value = "SELECT s FROM Supplier s  WHERE s.sex='nam' ")
    Page<Supplier> findAllBySex(String sex,Pageable pageable);

    @Query(value = "SELECT s FROM Supplier s  WHERE s.sex='nữ'  ")
    Page<Supplier> findUserBySex(String sex,Pageable pageable);

    @Query(" SELECT c FROM Supplier c WHERE c.sex='nữ' AND c.isConfirm=true AND c.isActive=true ORDER BY c.count DESC")
    List<Supplier> top8Female();

    @Query(" SELECT c FROM Supplier c WHERE c.sex='nam' AND c.isConfirm=true AND c.isActive=true ORDER BY c.count DESC")
    List<Supplier> top4Male();

    @Query(value = "SELECT s FROM Supplier s WHERE s.isConfirm=?1")
    Page<Supplier> findAllByConfirm(Boolean isConfirm,Pageable pageable);



}
