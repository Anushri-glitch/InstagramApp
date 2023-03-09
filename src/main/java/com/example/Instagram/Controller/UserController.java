package com.example.Instagram.Controller;

import com.example.Instagram.Model.User;
import com.example.Instagram.Service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/userApp")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value="/user")
    public ResponseEntity<String> saveUser(@RequestBody String userData){
        User user = setUser(userData);
        int userId = userService.saveUser(user);
        return new ResponseEntity<>("user saved with Id - " + userId, HttpStatus.CREATED);
    }

    private User setUser(String userData){
        JSONObject jsonObject = new JSONObject(userData);
        User user = new User();

        user.setAge(jsonObject.getInt("age"));
        user.setEmail(jsonObject.getString("Email"));
        user.setFirstName(jsonObject.getString("FirstName"));
        user.setLastName(jsonObject.getString("LastName"));
        user.setPhoneNumber(jsonObject.getString("phoneNumber"));

        return user;
    }
}
