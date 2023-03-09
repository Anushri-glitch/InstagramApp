package com.example.Instagram.Service;

import com.example.Instagram.Dao.IUserRepository;
import com.example.Instagram.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;
    public int saveUser(User user){
        User userObject = userRepository.save(user);
        return userObject.getUserId();
    }
}
