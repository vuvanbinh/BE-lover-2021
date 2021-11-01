package com.codegym.loverbe.security.userPrinciple;

import com.codegym.loverbe.model.User;
import com.codegym.loverbe.repository.IUserRepository;
import com.codegym.loverbe.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with -> username or mail:"+username)
        );
        return UserPrinciple.build(user);
    }

    //Lấy ra user hiện để thực hiện thao tác với DB;
    public User getCurrentUser(){
        Optional<User> user;
        String username;
        //Lấy một object principal trong SecurityContexHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //So sánh với Userdetails nếu mà đúng thì gắn userName = principal.getUsername();
        if (principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        }else {
            //nếu không phải user hiện tại thì userName = principal.toString();
            username = principal.toString();
        }
        //Kiểm tra nếu userName tồn tại trong db thì gắn user = hàm tìm kiếm trong db theo userName
        if (userRepository.existsByUsername(username)){
            user = userService.findByUsername(username);
        }else {
            //Nếu chưa tồn tại thì trả về một thể hiện của lớp User thông qua Optional.of
            user = Optional.of(new User());
            //set cho no 1 cai ten user an danh Day la truong hop ma tuong tac qua dang nhap kieu FB hay GG
            user.get().setUsername("Anonymous");
        }
        return user.get();
    }



}
