package com.codegym.loverbe.service.supplier;

import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService implements ISupplierService{
    @Autowired
    ISupplierRepository supplierRepository;

    @Override
    public Page<Supplier> pageFindAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    @Override
    public List<Supplier> top6() {
        return supplierRepository.top6();
    }

    @Override
    public Optional<Supplier> findByUser(User user) {
        return supplierRepository.findByUser(user);
    }

    public Supplier findByUserId(Long id){
        List<Supplier> supplierList = findAll();
        Supplier supplier1=null;
        for (Supplier s: supplierList
             ) {
            if (s.getUser().getId()==id){
                supplier1=s;
            }
        }
        return supplier1;
    }

    @Override
    public Iterable<Supplier> findByNameContaining(String name) {
        return supplierRepository.findByNameContaining(name);
    }

    @Override
    public List<Supplier> yearOfBirth1825() {
        return null;
    }

    @Override
    public Iterable<Supplier> findByCity(String city) {
        return supplierRepository.findByCity(city);
    }


    @Override
    public Iterable<Supplier> findBySex(String sex) {
        return supplierRepository.findBySex(sex);
    }

    @Override
    public List<Supplier> minView() {
        return supplierRepository.minView();
    }


    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public void remove(Long id) {
        supplierRepository.deleteById(id);
    }

    public void delete(Supplier supplier){
        supplierRepository.delete(supplier);
    }
}
