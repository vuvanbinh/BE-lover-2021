package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.Order;
import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.security.userPrinciple.UserDetailServiceImpl;
import com.codegym.loverbe.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    IOrderService orderService;
    @Autowired
    UserDetailServiceImpl userDetailService;

    @PostMapping
    public ResponseEntity<?>create(@RequestBody Order order){
        User user = userDetailService.getCurrentUser();
        order.setStatusOrder("Chờ phản hồi");
        order.setUser(user);
        orderService.save(order);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> pageFindAll() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/accomplished")
    public ResponseEntity<List<Order>> listAccomplished() {
        List<Order> orderList = orderService.listAccomplished();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

}
