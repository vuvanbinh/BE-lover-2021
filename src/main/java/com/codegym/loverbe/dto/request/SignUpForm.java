package com.codegym.loverbe.dto.request;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SignUpForm {
    private String name;
    private String username;
    private String password;
    private String email;
    private int phoneNumber;
    private String address;
    private String avatar;
    private LocalDate joinDate;
    private Boolean status;
    private Set<String> roles;
}
