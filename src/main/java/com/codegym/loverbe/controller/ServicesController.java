package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.Services;
import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.security.userPrinciple.UserDetailServiceImpl;
import com.codegym.loverbe.service.services.IServicesService;
import com.codegym.loverbe.service.supplier.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*")
public class ServicesController {

    @Autowired
    private IServicesService servicesService;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    ISupplierService supplierService;

    @GetMapping
    public ResponseEntity<Iterable<Services>> findAll(){
        return new ResponseEntity<>(servicesService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Services services){
        User user = userDetailService.getCurrentUser();
        services.setUser(user);
        Services services1= servicesService.save(services);
        Supplier supplier= supplierService.findByUserId(user.getId());
        if (supplier!=null){
            List<Services> servicesList= supplier.getServices();
            servicesList.add(services1);
            supplier.setServices(servicesList);
            supplierService.save(supplier);
        }
        return new ResponseEntity<>(new ResponseMessage("Create success!"),HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id")Services services,@RequestBody Services newServices){
        newServices.setId(services.getId());
        servicesService.save(newServices);
        return new ResponseEntity<>(new ResponseMessage("Update success!"),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        servicesService.remove(id);
        return new ResponseEntity<>(new ResponseMessage("Delete success!"),HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Services>>findById(@PathVariable("id") Optional<Services> services){
        return new ResponseEntity<>(services,HttpStatus.OK);
    }

    @GetMapping("findAllByUser")
    public ResponseEntity<Iterable<Services>>findById(){
        return new ResponseEntity<>(servicesService.findAllByUser(userDetailService.getCurrentUser()),HttpStatus.OK);
    }

}
