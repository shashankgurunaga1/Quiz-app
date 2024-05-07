package com.quiz.QuizApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.quiz.QuizApp.models.User;
import com.quiz.QuizApp.repositories.UserRepository;

@Service
public class UserServices {
    @Autowired
    UserRepository user_repo;

    public User createUser(User user1) {
        return user_repo.save(user1);

    }

    public User getUserbyId(Integer user_id) {

        return user_repo.findById(user_id).get();
    }

    public List<User> getUser() {
        System.out.println(" inside User services getUser");

        return user_repo.findAll();
    }

    public User updateUser(Integer user_id, User users) {
        System.out.println("inside UserServices.java update , print userid " + user_id);

        User id = user_repo.findById(user_id).get();
        id.setFirst_name(users.getFirst_name());
        id.setLast_name(users.getLast_name());
        id.setEmail(users.getEmail());
        id.setPhone_no(users.getPhone_no());
        id.setRole(users.getRole());
        return user_repo.save(id);

    }

    public void deleteUser(Integer user_id) {
        user_repo.deleteById(user_id);
    }
}
