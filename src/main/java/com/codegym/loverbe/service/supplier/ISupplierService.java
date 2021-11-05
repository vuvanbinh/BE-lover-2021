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
    Iterable<Supplier> findByName(String name);
    Page<Supplier> yearOfBirth1823(Pageable pageable);
    List<Supplier> yearOfBirth2327();
    List<Supplier> yearOfBirth2731();
    Iterable<Supplier> findByCity(String city);
    Iterable<Supplier> findBySex(String sex);
    List<Supplier> minView();


}
