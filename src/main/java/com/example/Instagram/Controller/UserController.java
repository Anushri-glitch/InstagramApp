package com.example.Instagram.Controller;

import com.example.Instagram.Model.User;
import com.example.Instagram.Service.UserService;
import com.example.Instagram.dto.SignInInput;
import com.example.Instagram.dto.SignInOutput;
import com.example.Instagram.dto.SignUpInput;
import com.example.Instagram.dto.SignUpOutput;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/userApp")
public class UserController {

    @Autowired
    UserService userService;

    //SignUp
    @PostMapping("/signup")
    public SignUpOutput signUp (@RequestBody SignUpInput signUpDto){
        return userService.signUp(signUpDto);
    }

    //SignIn
    @PostMapping("/signIn")
    public SignInOutput signIn (@RequestBody SignInInput signInDto){
        return userService.signIn(signInDto);
    }

//    @PostMapping(value="/user")
//    public ResponseEntity<String> saveUser(@RequestBody String userData){
//        User user = setUser(userData);
//        int userId = userService.saveUser(user);
//        return new ResponseEntity<>("user saved with Id - " + userId, HttpStatus.CREATED);
//    }

    @GetMapping("getUser")
    public ResponseEntity<String> getUser(@Nullable @RequestParam String userId){
        JSONArray userDetails = userService.getUser(userId);
        return new ResponseEntity(userDetails.toString(), HttpStatus.OK);
    }

    @PutMapping("updateUser/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody String userData){
        User user = setUser(userData);
        userService.updateUser(user, userId);
        return new ResponseEntity("user updated", HttpStatus.OK);
    }

    private User setUser(String userData){
        JSONObject jsonObject = new JSONObject(userData);
        User user = new User();

        user.setUserFirstName(jsonObject.getString("userFirstName"));
        user.setUserLastName(jsonObject.getString("userLastName"));
        user.setUserAge(jsonObject.getInt("userAge"));
        user.setUserEmail(jsonObject.getString("userEmail"));
        user.setUserPassword(jsonObject.getString("userPassword"));
        user.setUserPhoneNumber(jsonObject.getString("userPhoneNumber"));

        return user;
    }
}
