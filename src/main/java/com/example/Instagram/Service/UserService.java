package com.example.Instagram.Service;

import com.example.Instagram.Dao.IUserRepository;
import com.example.Instagram.Model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;
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
        newUserObject.put("firstName", user.getFirstName());
        newUserObject.put("lastName", user.getLastName());
        newUserObject.put("age", user.getAge());
        newUserObject.put("Email", user.getEmail());
        newUserObject.put("phoneNumber", user.getPhoneNumber());

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
}
