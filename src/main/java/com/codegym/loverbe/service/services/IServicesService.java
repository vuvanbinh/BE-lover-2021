package com.codegym.loverbe.service.services;

import com.codegym.loverbe.model.Services;
import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface IServicesService extends IService<Services> {
    Iterable<Services>findAllByUser(User user);

}
