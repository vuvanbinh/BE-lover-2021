package com.codegym.loverbe.dto.request;

import lombok.Data;

@Data
public class SignInForm {
    private String username;
    private String password;
}