package com.codegym.loverbe.controller;

import com.codegym.loverbe.model.Services;
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
    @GetMapping
    public ResponseEntity<Iterable<Services>> findAll() {
        Iterable<Services> servicesIterable = servicesService.findAll();
        return new ResponseEntity<>(servicesIterable, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Services> create(@RequestBody Services services) {
        servicesService.save(services);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Services> deleteById(@PathVariable Long id) {
        Optional<Services> servicesOptional = servicesService.findById(id);
        if (!servicesOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        servicesService.remove(id);
        return new ResponseEntity<>(servicesOptional.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Services> edit(@PathVariable Long id, @RequestBody Services services) {
        Optional<Services> servicesOptional = servicesService.findById(id);
        if (!servicesOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        services.setId(id);
        servicesService.save(services);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Services> findById(@PathVariable Long id) {
        Optional<Services> servicesOptional = servicesService.findById(id);
        if (!servicesOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicesOptional.get(), HttpStatus.OK);
    }
}
