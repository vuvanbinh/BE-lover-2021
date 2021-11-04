package com.codegym.loverbe.repository;

import com.codegym.loverbe.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image,Long> {
}
