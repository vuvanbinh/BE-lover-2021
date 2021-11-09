package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.Order;
import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.service.order.IOrderService;
import com.codegym.loverbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    IUserService userService;

    @Autowired
    private IOrderService orderService;

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("block/{id}")
    public ResponseEntity<?> blockUser(@PathVariable("id") User user){
        Boolean isBock =  user.getIsBlock();
        user.setIsBlock(!isBock);
        userService.save(user);
        return new ResponseEntity<>(new ResponseMessage("Success!"),HttpStatus.OK);
    }

    @GetMapping("/allOrder")
    public ResponseEntity<Iterable<Order>> findAllOrder(){
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/accomplished")
    public ResponseEntity<List<Order>> listAccomplished() {
        List<Order> orderList = orderService.listAccomplished();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/wait")
    public ResponseEntity<List<Order>> listWait() {
        List<Order> orderList = orderService.listWait();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/received")
    public ResponseEntity<List<Order>> listReceived() {
        List<Order> orderList = orderService.listReceived();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

}
