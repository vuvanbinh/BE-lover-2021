package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.Services;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.security.userPrinciple.UserDetailServiceImpl;
import com.codegym.loverbe.service.services.IServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*")
public class ServicesController {

    @Autowired
    private IServicesService servicesService;
    @Autowired
    UserDetailServiceImpl userDetailService;

    @GetMapping
    public ResponseEntity<Iterable<Services>> findAll(){
        return new ResponseEntity<>(servicesService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Services services){
        services.setUser(userDetailService.getCurrentUser());
        servicesService.save(services);
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

    @GetMapping("findAllByUser/{id}")
    public ResponseEntity<Iterable<Services>>findById(@PathVariable("id")User user){
        return new ResponseEntity<>(servicesService.findAllByUser(user),HttpStatus.OK);
    }
}
