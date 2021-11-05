package com.codegym.loverbe.service.services;

import com.codegym.loverbe.model.Services;
import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.repository.IServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesService implements IServicesService{
    @Autowired
    IServicesRepository servicesRepository;

    @Override
    public Page<Services> pageFindAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Services> findAll() {
        return servicesRepository.findAll();
    }

    @Override
    public Optional<Services> findById(Long id) {
        return servicesRepository.findById(id);
    }

    @Override
    public Services save(Services services) {
        return servicesRepository.save(services);
    }

    @Override
    public void remove(Long id) {
        servicesRepository.deleteById(id);
    }

    @Override
    public Iterable<Services> findAllByUser(User user) {
        return servicesRepository.findAllByUser(user);
    }
}
