package com.codegym.loverbe.repository;

import com.codegym.loverbe.model.Image;
import com.codegym.loverbe.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepository extends JpaRepository<Image,Long> {
    Iterable<Image> findAllBySupplier(Supplier supplier);
}
