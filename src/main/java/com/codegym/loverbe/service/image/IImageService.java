package com.codegym.loverbe.service.image;

import com.codegym.loverbe.model.Image;
import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface IImageService extends IService<Image> {
    Iterable<Image> findAllBySupplier(Supplier supplier);
}
