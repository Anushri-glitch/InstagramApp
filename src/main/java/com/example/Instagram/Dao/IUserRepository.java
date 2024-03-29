package com.example.Instagram.Dao;

import com.example.Instagram.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findFirstByUserEmail(String email);
}
