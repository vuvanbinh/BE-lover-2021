package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.security.userPrinciple.UserDetailServiceImpl;
import com.codegym.loverbe.service.supplier.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {
  @Autowired
  private ISupplierService supplierService;
  @Autowired
  private UserDetailServiceImpl userDetailService;

//  @GetMapping
//  public ResponseEntity<Iterable<Supplier>> showAllSupplier(){
//      Iterable<Supplier> listSupplier = supplierService.findAll();
//      return new ResponseEntity<>(listSupplier, HttpStatus.OK);
//  }
@GetMapping
public ResponseEntity<?>pageFindAll(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
    Page<Supplier> categoryPage = supplierService.pageFindAll(pageable);
    if (categoryPage.isEmpty()){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else {
        return new ResponseEntity<>(categoryPage,HttpStatus.OK);
    }
}

  @PostMapping
  public ResponseEntity<?> createSupplier(@RequestBody Supplier supplier){
      supplier.setUser(userDetailService.getCurrentUser());
      supplier.setActiveStatus(true);
      supplierService.save(supplier);
      return new ResponseEntity<>(new ResponseMessage("Create supplier success"), HttpStatus.OK);
  }
  @PutMapping
    public ResponseEntity<?> editSupplier(@RequestBody Supplier newSupplier){
      newSupplier.setUser(userDetailService.getCurrentUser());
      supplierService.save(newSupplier);
      return new ResponseEntity<>(new ResponseMessage("Edit supplier success"),HttpStatus.OK);
  }

}
