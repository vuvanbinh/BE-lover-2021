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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    @Autowired
    JavaMailSender javaMailSender;



    @GetMapping("findAllByUser")
    public ResponseEntity<?> findAllByUser(){
        User user = userDetailService.getCurrentUser();
        List<Order> orderList = orderService.findAllByUser(user);
        if (orderList.isEmpty()){
            return new ResponseEntity<>(new ResponseMessage("Is empty!"),HttpStatus.OK);
        }else return new ResponseEntity<>(orderList,HttpStatus.OK);
    }

    @GetMapping("findAllBySupplier/{id}")
    public ResponseEntity<?> findAllBySupplier(@PathVariable("id") Supplier supplier){
        List<Order> orderList = orderService.findAllBySupplier(supplier);
        if (orderList.isEmpty()){
            return new ResponseEntity<>(new ResponseMessage("Is empty!"),HttpStatus.OK);
        }else return new ResponseEntity<>(orderList,HttpStatus.OK);
    }


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
    public ResponseEntity<List<Order>> listAccomplished(@PathVariable User id) {
        List<Order> orderList = orderService.listAccomplished(id);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    // Viết API xác nhận đơn thuê và gửi tin nhắn bằng email
  @PutMapping("changeOrderStatus/{id}")
    public ResponseEntity<?>confirm(@PathVariable("id") Order order){
            String status = order.getStatusOrder();
            SimpleMailMessage sendmail = new SimpleMailMessage();
            switch (status){
                case "Chờ phản hồi":
                    order.setStatusOrder("Đã nhận");
                    orderService.save(order);
                    sendmail.setTo(order.getUser().getEmail());
                    sendmail.setSubject("Người yêu mà bạn thuê đã xác nhận đơn rồi");
                    sendmail.setText("Người yêu mà bạn thuê đã xác nhận đơn rồi");
                    javaMailSender.send(sendmail);
                    break;
                case "Đã nhận":
                    order.setStatusOrder("Đã hoàn thành");
                    orderService.save(order);
                    break;
            }
        return new ResponseEntity<>(new ResponseMessage("Update success!"), HttpStatus.OK);
    }
//Viết API cho detail của tất cả các đơn
    @GetMapping("{id}")
    public ResponseEntity<?> detailOrder(@PathVariable("id") Long idOrder){
        Order order = orderService.findById(idOrder).get();
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("feedback/{id}")
    public ResponseEntity<?> changeFeedback(@PathVariable("id") Order order, @RequestParam("feedback")String feedback ){
        order.setFeedback(feedback);
        orderService.save(order);
        return new ResponseEntity<>(new ResponseMessage("Update success!"),HttpStatus.OK);
    }


}
