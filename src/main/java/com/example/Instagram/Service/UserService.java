package com.example.Instagram.Service;

import com.example.Instagram.Model.AuthenticationToken;
import com.example.Instagram.Dao.IUserRepository;
import com.example.Instagram.Model.User;
import com.example.Instagram.dto.SignInInput;
import com.example.Instagram.dto.SignInOutput;
import com.example.Instagram.dto.SignUpInput;
import com.example.Instagram.dto.SignUpOutput;
import jakarta.xml.bind.DatatypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;
    
    @Autowired
    AuthenticationService authService;
    
    public int saveUser(User user){
        User userObject = userRepository.save(user);
        return userObject.getUserId();
    }

    public JSONArray getUser(String userId){
        JSONArray jsonArray = new JSONArray();
        if(null != userId && userRepository.findById(Integer.valueOf(userId)).isPresent()){
            User user = userRepository.findById(Integer.valueOf(userId)).get();
            if(null != user){
                JSONObject userObject = setUser(user);
                jsonArray.put(userObject);
            }
        }
        else{
            List<User> userList = userRepository.findAll();
            for(User user : userList){
                JSONObject userObject = setUser(user);
                jsonArray.put(userObject);
            }
        }
        return jsonArray;
    }

    //This setUserObject Function is only for getUser Function
    private JSONObject setUser(User user){
        JSONObject newUserObject = new JSONObject();

        newUserObject.put("userId", user.getUserId());
        newUserObject.put("firstName", user.getUserFirstName());
        newUserObject.put("lastName", user.getUserLastName());
        newUserObject.put("age", user.getUserAge());
        newUserObject.put("Email", user.getUserEmail());
        newUserObject.put("phoneNumber", user.getUserPhoneNumber());

        return newUserObject;
    }

    public void updateUser(User newUser, String userId) {
        if(userRepository.findById(Integer.valueOf(userId)).isPresent()){
            User user = userRepository.findById(Integer.valueOf(userId)).get();
            newUser.setUserId(user.getUserId());
            userRepository.save(newUser);
//            user.setEmail(newUser.getEmail());
//            user.setPhoneNumber(newUser.getPhoneNumber());
//            user.setFirstName(newUser.getFirstName());
//            user.setLastName(newUser.getLastName());
//            user.setAge(newUser.getAge());
        }
    }

    public SignUpOutput signUp(SignUpInput signUpDto) {
        //check if user exists or not based on email
        User user = userRepository.findFirstByUserEmail(signUpDto.getUserEmail());

        if (user != null) {
            throw new IllegalStateException("User already Exists!!!.....Make new registration!!!");
        }

        //encryption
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //save the user
        user = new User(signUpDto.getUserFirstName(), signUpDto.getUserLastName(), signUpDto.getUserAge(), signUpDto.getUserEmail(),
                encryptedPassword, signUpDto.getUserContact());
        userRepository.save(user);

        //token creation and saving
        AuthenticationToken token = new AuthenticationToken(user);
        authService.saveToken(token);

        return new SignUpOutput("User Registered!!!", "User Created Successfully!!!");

    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        md5.update(userPassword.getBytes());
        byte[] digested = md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);
        return hash;
    }

    public SignInOutput signIn(SignInInput signInDto) {
        //check Email
        User user = userRepository.findFirstByUserEmail(signInDto.getUserEmail());
        if (user == null) {
            throw new IllegalStateException("User Invalid!!!.....Make new registration!!!");
        }

        //encrypt Password
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signInDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //match it with database encrypted password
        boolean isPasswordValid = encryptedPassword.equals(user.getUserPassword());
        if(!isPasswordValid){
            throw new IllegalStateException("User Invalid!!....Signup Again!!");
        }

        //figure out the token
        AuthenticationToken authToken = authService.getToken(user);

        //setup output response
        return new SignInOutput("Authentication Successful!!!", authToken.getToken());
    }
}
