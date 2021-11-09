package com.codegym.loverbe.service.order;

import com.codegym.loverbe.model.Order;
import com.codegym.loverbe.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService extends IService<Order> {
    List<Order> listAccomplished();
    List<Order> listWait();
    List<Order> listReceived();

}
