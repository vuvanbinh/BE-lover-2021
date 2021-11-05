package com.codegym.loverbe.service.image;

import com.codegym.loverbe.model.Image;
import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.repository.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService implements IImageService{
    @Autowired
    IImageRepository imageRepository;
    @Override
    public Page<Image> pageFindAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Image> findAll() {
        return null;
    }

    @Override
    public Optional<Image> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void remove(Long id) {
    }

    @Override
    public Iterable<Image> findAllBySupplier(Supplier supplier) {
        return imageRepository.findAllBySupplier(supplier);
    }
}
