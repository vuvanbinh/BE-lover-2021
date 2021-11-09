package com.codegym.loverbe.repository;

import com.codegym.loverbe.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {
    @Query(value = "select o from Order o where o.statusOrder='đã hoàn thành' ")
    List<Order> listAccomplished();

    @Query(value = "select o from Order o where o.statusOrder='chờ phản hồi' ")
    List<Order> listWait();

    @Query(value = "select o from Order o where o.statusOrder='đã nhận' ")
    List<Order> listReceived();
}
