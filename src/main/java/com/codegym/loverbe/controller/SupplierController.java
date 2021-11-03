package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.security.userPrinciple.UserDetailServiceImpl;
import com.codegym.loverbe.service.supplier.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {
    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    ISupplierService supplierService;

    @PostMapping()
    public ResponseEntity<?>create(@RequestBody Supplier supplier){
        supplier.setUser(userDetailService.getCurrentUser());
        supplier.setActiveStatus(true);
        supplierService.save(supplier);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }

    @GetMapping("/top6")
    public ResponseEntity<List<Supplier>> findTop6New() {
        List<Supplier> supplierList = supplierService.top6();
        List<Supplier> listtop = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            listtop.add(supplierList.get(i));
        }
        return new ResponseEntity<>(listtop, HttpStatus.OK);

    }


    @GetMapping("/count/{id}")
    public ResponseEntity<Supplier> setCount(@PathVariable Long id) {
        Optional<Supplier> userOptional = supplierService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Supplier supplier = userOptional.get();
        Integer count = supplier.getCount();
        supplier.setCount(count+1);
        supplier.setId(id);
        supplierService.save(supplier);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }



}
