package com.codegym.loverbe.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private int phoneNumber;
    private String address;
    private String avatar;
    private LocalDate joinDate;
    private Boolean status;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<Image> images;
    private int yearOfBirth;
    private String sex;
    private String country;
    private String city;
    private int height;
    private int weight;
    private String interests;
    private String description;
    private String requirements;
    private String linkFB;
    private int count;










}
