package com.example.Instagram.Service;

import com.example.Instagram.Dao.ITokenRepo;
import com.example.Instagram.Model.AuthenticationToken;
import com.example.Instagram.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    ITokenRepo tokenRepo;

    public void saveToken(AuthenticationToken token){
        tokenRepo.save(token);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepo.findByUser(user);
    }
}
