package com.codegym.loverbe.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SupplierForm {
    private String name;
    private int yearOfBirth;
    private String sex;
    private String city;
    private String country;
    private int height;
    private int weight;
    private String interests;
    private String description;
    private String requirements;
    private String linkFB;
    private List<String> images;
}
