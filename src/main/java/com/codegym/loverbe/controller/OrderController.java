package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.Order;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.security.userPrinciple.UserDetailServiceImpl;
import com.codegym.loverbe.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<?>create(@RequestBody Order order){
        User user = userDetailService.getCurrentUser();
        order.setStatusOrder("Chờ phản hồi");
        order.setUser(user);
        orderService.save(order);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
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
        return new ResponseEntity<>(new ResponseMessage("Thay doi thanh cong"), HttpStatus.OK);
    }
//Viết API cho detail của tất cả các đơn
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detailOrder(@PathVariable("id") Long idOrder){
        Order order = orderService.findById(idOrder).get();
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
