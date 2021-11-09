package com.codegym.loverbe.service.order;

import com.codegym.loverbe.model.Order;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService{

    @Autowired
    IOrderRepository orderRepository;

    @Override
    public Page<Order> pageFindAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void remove(Long id) {
    }

    @Override
    public List<Order> listAccomplished(User user) {
        return orderRepository.listAccomplished(user);
    }

    @Override
    public List<Order> listWait( ) {
        return orderRepository.listWait();
    }

    @Override
    public List<Order> listReceived() {
        return orderRepository.listReceived();
    }
}
