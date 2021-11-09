package com.codegym.loverbe.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Table(name = "orders")
@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String address;
    @ManyToOne
    private Supplier supplier;
    @OneToMany
    private List<Services> services;
    private int totalTime;
    private int hourStart;
    private LocalDate dayStart;
        private int totalMoney;
    private String statusOrder;
    private String feedback;
}
