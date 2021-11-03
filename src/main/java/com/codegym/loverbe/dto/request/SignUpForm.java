package com.codegym.loverbe.dto.request;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SignUpForm {
    private String username;
    private String password;
    private String email;
    private int phoneNumber;
    private String avatar;
    private LocalDate joinDate;
    private Boolean isBlock;
    private Set<String> roles;
}
