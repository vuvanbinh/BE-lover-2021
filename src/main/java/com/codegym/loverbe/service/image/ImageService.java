package com.codegym.loverbe.service.image;

import com.codegym.loverbe.model.Image;
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
    IImageRepository iImageRepository;
    @Override
    public Page<Image> pageFindAll(Pageable pageable) {
        return iImageRepository.findAll(pageable);
    }

    @Override
    public List<Image> findAll() {
        return iImageRepository.findAll();
    }

    @Override
    public Optional<Image> findById(Long id) {
        return iImageRepository.findById(id);
    }

    @Override
    public void save(Image image) {
        iImageRepository.save(image);
    }

    @Override
    public void remove(Long id) {
        iImageRepository.deleteById(id);

    }
}
