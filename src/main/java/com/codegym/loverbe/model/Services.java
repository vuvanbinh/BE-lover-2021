package com.codegym.loverbe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String serviceType;
    private int price;
    @ManyToOne
    private User user;

}
