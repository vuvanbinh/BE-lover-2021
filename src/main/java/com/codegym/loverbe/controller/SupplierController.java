package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.request.SearchForm;
import com.codegym.loverbe.dto.request.SupplierForm;
import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.*;
import com.codegym.loverbe.security.userPrinciple.UserDetailServiceImpl;
import com.codegym.loverbe.service.image.IImageService;
import com.codegym.loverbe.service.role.IRoleService;
import com.codegym.loverbe.service.services.IServicesService;
import com.codegym.loverbe.service.supplier.ISupplierService;
import com.codegym.loverbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    IServicesService servicesService;
    @Autowired
    ISupplierService supplierService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IUserService userService;

    @Autowired
    IImageService imageService;


    @GetMapping
    public ResponseEntity<List<Supplier>> pageFindAll() {
        return new ResponseEntity<>(supplierService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody SupplierForm supplierForm) {
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
        images.forEach((img) -> {
            Image image = new Image();
            image.setImg(img);
            image.setSupplier(supplier1);
            imageService.save(image);
        });
        //Lấy ra List<image> vừa lưu vào bằng supplier của nó, rồi setImages của supplier của nó bằng với list<Image>
        List<Image> imageList = (List<Image>) imageService.findAllBySupplier(supplier1);
        supplier1.setImages(imageList);

        List<Services> servicesList = (List<Services>) servicesService.findAllByUser(supplier1.getUser());
        supplier1.setServices(servicesList);
        supplierService.save(supplier1);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Supplier supplier) {
        int view = supplier.getView();
        supplier.setView(view + 1);
        return new ResponseEntity<>(supplierService.save(supplier), HttpStatus.OK);
    }

    @GetMapping("findByUser")
    public ResponseEntity<?> findByUser() {
        User user = userDetailService.getCurrentUser();
        return new ResponseEntity<>(supplierService.findByUserId(user.getId()), HttpStatus.OK);
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
        supplier.setCount(count + 1);
        supplier.setId(id);
        supplierService.save(supplier);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }


    @GetMapping("pageFindAllByIsConfirmAndActive/{isConfirm}/{isActive}")
    public ResponseEntity<?> pageFindAllByIsConfirm
            (@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable
                    , @PathVariable("isConfirm") Boolean isConfirm, @PathVariable("isActive") Boolean isActive) {

        Page<Supplier> supplierPage = supplierService.findAllByConfirmAndActive(isConfirm, isActive, pageable);
        if (supplierPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(supplierPage, HttpStatus.OK);
        }
    }

    @PostMapping("changeIsConfirm/{id}")
    public ResponseEntity<?> changeIsConfirm(@PathVariable("id") Supplier supplier) {
        supplier.setConfirm(true);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(RoleName.PM).get());

        User user = supplier.getUser();
        user.setRoles(roles);
        userService.save(user);
        supplierService.save(supplier);
        return new ResponseEntity<>(new ResponseMessage("Success!"), HttpStatus.OK);
    }

    @PostMapping("changeIsActive/{id}")
    public ResponseEntity<?> changeIsActive(@PathVariable("id") Supplier supplier) {
        Boolean isActive = !supplier.isActive();
        supplier.setActive(isActive);
        supplierService.save(supplier);
        return new ResponseEntity<>(new ResponseMessage("Change IsActive success!"), HttpStatus.OK);
    }

    @GetMapping("search/{name}")
    public ResponseEntity<?> findAllByNameContaining(@PathVariable("name") String name
            , @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Supplier> supplierPage = supplierService.findAllByNameContaining(name, pageable);
        if (supplierPage.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Is empty"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(supplierPage, HttpStatus.OK);
        }
    }

    @PostMapping("search")
    public ResponseEntity<?> search(@RequestBody SearchForm searchForm
            , @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Supplier> supplierPage = supplierService.search(
                searchForm.getName()
                , searchForm.getMinYear()
                , searchForm.getMaxYear()
                , searchForm.getSex()
                , searchForm.getCity()
                , pageable);
        if (supplierPage.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Is empty"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(supplierPage, HttpStatus.OK);
        }
    }

    @GetMapping("/vip/{id}")
    public ResponseEntity<Supplier> setVip(@PathVariable Long id) {
        Optional<Supplier> userOptional = supplierService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Supplier supplier = userOptional.get();
        supplier.setVip(true);
        supplier.setId(id);
        supplierService.save(supplier);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/12Female")
    public ResponseEntity<List<Supplier>> find12SupByFemale() {
        List<Supplier> supplierList = supplierService.find12SupByFemale();
        List<Supplier> listtop = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int max = supplierList.size();
            int min = 0;
            int range = max - min;
            int index  = (int)(Math.random() * range) + min;
            listtop.add(supplierList.get(index));
        }
        return new ResponseEntity<>(listtop, HttpStatus.OK);

    }

    @GetMapping("/12Male")
    public ResponseEntity<List<Supplier>> find12SupByMale() {
        List<Supplier> supplierList = supplierService.find12SupByMale();
        List<Supplier> listtop = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int max = supplierList.size();
            int min = 0;
            int range = max - min;
            int index  = (int)(Math.random() * range) + min;
            listtop.add(supplierList.get(index));
        }
        return new ResponseEntity<>(listtop, HttpStatus.OK);

    }
    }
