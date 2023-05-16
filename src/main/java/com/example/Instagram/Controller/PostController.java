package com.example.Instagram.Controller;

import com.example.Instagram.Dao.IUserRepository;
import com.example.Instagram.Model.Post;
import com.example.Instagram.Model.User;
import com.example.Instagram.Service.PostService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    PostService postService;

    @PostMapping(value="/post")
    //URL - localhost:8080/post
    public ResponseEntity<String> savePost(@RequestBody String postRequest){
        Post post = setPost(postRequest);
        int postId = postService.savePost(post);
        return new ResponseEntity<>("post saved with Id - " + postId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/post")
    public List<Post> getPost(){
        return postService.getPost();
    }

    private Post setPost(String postRequest) {
        JSONObject jsonObject = new JSONObject(postRequest);

        User user = null;
        int userId = jsonObject.getInt("userId");
        if(userRepository.findById(userId).isPresent()){
            user = userRepository.findById(userId).get();
        }
        else{
            return null;
        }

        Post post = new Post();
        post.setUser(user);
        post.setPostData(jsonObject.getString("postData"));
        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        post.setCreateDate(createdTime);
        return post;
    }

}
