package com.codegym.loverbe.controller;

import com.codegym.loverbe.dto.request.SignInForm;
import com.codegym.loverbe.dto.request.SignUpForm;
import com.codegym.loverbe.dto.response.JwtResponse;
import com.codegym.loverbe.dto.response.ResponseMessage;
import com.codegym.loverbe.model.Role;
import com.codegym.loverbe.model.RoleName;
import com.codegym.loverbe.model.User;
import com.codegym.loverbe.security.jwt.JwtAuthTokenFilter;
import com.codegym.loverbe.security.jwt.JwtProvider;
import com.codegym.loverbe.security.userPrinciple.UserDetailServiceImpl;
import com.codegym.loverbe.security.userPrinciple.UserPrinciple;
import com.codegym.loverbe.service.role.IRoleService;
import com.codegym.loverbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtAuthTokenFilter jwtAuthTokenFilter;
    @Autowired
    UserDetailServiceImpl userDetailService;

    @PostMapping("/signIn")
    public ResponseEntity<?> login(@RequestBody SignInForm signInForm) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.createToken(authentication);
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtResponse(
                    token
                    ,userPrinciple.getId()
                    ,userPrinciple.getName()
                    , userPrinciple.getEmail()
                    , userPrinciple.getPhoneNumber()
                    , userPrinciple.getAddress()
                    , userPrinciple.getAvatar()
                    , userPrinciple.getJoinDate()
                    ,userPrinciple.getStatus()
                    , userPrinciple.getAuthorities()));
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage("no"), HttpStatus.OK);
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if  (userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponseMessage("Username is existed!"), HttpStatus.OK);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Email is existed!"), HttpStatus.OK);
        }

        User user = new User();
        user.setName(signUpForm.getName());
        user.setUsername(signUpForm.getUsername());
        user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        user.setEmail(signUpForm.getEmail());
        user.setPhoneNumber(signUpForm.getPhoneNumber());
        Set<String> strRole = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRole.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                case "user":
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
                    break;
                default:
                    Role pMRole = roleService.findByName(RoleName.PM).orElseThrow(()-> new RuntimeException("Role not found"));
                    roles.add(pMRole);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }




}
