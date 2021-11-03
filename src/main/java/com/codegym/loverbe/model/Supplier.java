package com.codegym.loverbe.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int yearOfBirth;
    private String sex;
    private String city;
    private String country;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Image> images;
    private int height;
    private int weight;
    private String interests;
    private String description;
    private String requirements;
    private String linkFB;
    private int count;
    private boolean activeStatus;
    @OneToOne
    private User user;

}
