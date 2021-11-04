package com.codegym.loverbe.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SignInForm {
    private String username;
    private String password;

}