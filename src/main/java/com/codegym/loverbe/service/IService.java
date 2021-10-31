package com.codegym.loverbe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IService<B>{
    Page<B> pageFindAll(Pageable pageable);
    Iterable<B> findAll();
    Optional<B> findById(Long id);
    void save(B b);
    void remove(Long id);
}
