package com.codegym.loverbe.controller;
import com.codegym.loverbe.model.Image;
import com.codegym.loverbe.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*")
public class ImageController {
    @Autowired
    IImageService imageService;

    @GetMapping
    public ResponseEntity<Iterable<Image>> findAll() {
        Iterable<Image> productIterable = imageService.findAll();
        return new ResponseEntity<>(productIterable, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Image> createNewProduct(@RequestBody Image image) {
        imageService.save(image);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Image> deleteById(@PathVariable Long id) {
        Optional<Image> imageOptional = imageService.findById(id);
        if (!imageOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        imageService.remove(id);
        return new ResponseEntity<>(imageOptional.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> editProduct(@PathVariable Long id, @RequestBody Image image) {
        Optional<Image> productOptional = imageService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        image.setId(id);imageService.save(image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
