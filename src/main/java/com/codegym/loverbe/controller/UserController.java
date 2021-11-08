package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    IUserService userService;

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

}
