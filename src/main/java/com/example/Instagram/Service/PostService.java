package com.example.Instagram.Service;

import com.example.Instagram.Dao.IPostRepository;
import com.example.Instagram.Model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    IPostRepository postRepository;
    public int savePost(Post post) {
        Post savedPost = postRepository.save(post);
        return savedPost.getPostId();
    }

    public List<Post> getPost() {
        return postRepository.findAll();
    }
}
