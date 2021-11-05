package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.request.SupplierForm;
import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.Image;
import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.security.userPrinciple.UserDetailServiceImpl;
import com.codegym.loverbe.service.image.IImageService;
import com.codegym.loverbe.service.supplier.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {
    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    ISupplierService supplierService;

    @Autowired
    IImageService imageService;

    @PostMapping()
    public ResponseEntity<?>create(@RequestBody SupplierForm supplierForm){
        //chuyển tất cả dữ liệu từ supplierFrom sang supplier rồi save lại
        Supplier supplier = new Supplier();
        supplier.setName(supplierForm.getName());
        supplier.setYearOfBirth(supplierForm.getYearOfBirth());
        supplier.setSex(supplierForm.getSex());
        supplier.setCity(supplierForm.getCity());
        supplier.setCountry(supplierForm.getCountry());
        supplier.setHeight(supplierForm.getHeight());
        supplier.setWeight(supplierForm.getWeight());
        supplier.setInterests(supplierForm.getInterests());
        supplier.setDescription(supplierForm.getDescription());
        supplier.setRequirements(supplierForm.getRequirements());
        supplier.setLinkFB(supplierForm.getLinkFB());
        User user = userDetailService.getCurrentUser();
        supplier.setUser(user);
        supplierService.save(supplier);

        //Lấy List<String> images từ supplierForm và lấy ra supplier vừa đã save(thì lúc này supplier có id)
        // duyệt for save lần lượt vào database của bảng image
        List<String> images = supplierForm.getImages();
        Supplier supplier1 = supplierService.findByUserId(user.getId());
        images.forEach((img)->{
            Image image = new Image();
            image.setImg(img);
            image.setSupplier(supplier1);
            imageService.save(image);
        });
        //Lấy ra List<image> vừa lưu vào bằng supplier của nó, rồi setImages của supplier của nó bằng với list<Image>
        List<Image> imageList=(List)imageService.findAllBySupplier(supplier1);
        supplier1.setImages(imageList);
        supplierService.save(supplier1);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?>pageFindAll(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Supplier> categoryPage = supplierService.pageFindAll(pageable);
        if (categoryPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(categoryPage,HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?>findById(@PathVariable("id")Supplier supplier){
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }

    @GetMapping("findByUser")
    public ResponseEntity<?>findByUser(){
        User user = userDetailService.getCurrentUser();
        return new ResponseEntity<>(supplierService.findByUserId(user.getId()),HttpStatus.OK);
    }



    @GetMapping("/top6")
    public ResponseEntity<List<Supplier>> findTop6New() {
        List<Supplier> supplierList = supplierService.top6();
        List<Supplier> listtop = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            listtop.add(supplierList.get(i));
        }
        return new ResponseEntity<>(listtop, HttpStatus.OK);

    }

    @GetMapping("/count/{id}")
    public ResponseEntity<Supplier> setCount(@PathVariable Long id) {
        Optional<Supplier> userOptional = supplierService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Supplier supplier = userOptional.get();
        Integer count = supplier.getCount();
        supplier.setCount(count+1);
        supplier.setId(id);
        supplierService.save(supplier);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }
//    @GetMapping("/find12")
//    public ResponseEntity<Iterable<Supplier>> findAll() {
//        Iterable<Supplier> supplierIterable = supplierService.findAll();
//        Iterable<Supplier> suppliers=new ArrayList<>();
//        for (int i = 0; i <= 12; i++) {
//            if (suppliers.)
//        }
//        return new ResponseEntity<>(supplierIterable, HttpStatus.OK);
//
//    }

}
