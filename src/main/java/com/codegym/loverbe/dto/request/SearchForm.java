package com.codegym.loverbe.dto.request;

import lombok.Data;

@Data
public class SearchForm {
    String name;
    int maxYear;
    int minYear;
    String sex;
    String city;
}
