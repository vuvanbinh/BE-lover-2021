package com.codegym.loverbe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IService<B>{
    Page<B> pageFindAll(Pageable pageable);
    List<B> findAll();
    Optional<B> findById(Long id);
    B save(B b);
    void remove(Long id);
}
