package com.codegym.loverbe.service.supplier;

import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ISupplierService extends IService<Supplier>{
    List<Supplier> top6();
    Optional<Supplier>findByUser(User user);
    Supplier findByUserId(Long id);


    Page<Supplier> findAllByConfirm(Boolean isConfirm, Pageable pageable);
    Page<Supplier> findSupplierBySex(String sex,Pageable pageable);
    Page<Supplier> findUserBySex(String sex,Pageable pageable);
    List<Supplier> top8Female();
    List<Supplier> top4Male();



}
