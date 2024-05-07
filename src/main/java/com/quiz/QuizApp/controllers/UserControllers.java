package com.quiz.QuizApp.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.quiz.QuizApp.models.User;
import com.quiz.QuizApp.services.UserServices;
import java.io.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/quiz")

public class UserControllers {
    @Autowired
    UserServices u_service;

    // Inserting users in the database
    @PostMapping("/adduser")
    @PreAuthorize("hasAuthority('admin:write')")
    public User addUsers(@RequestBody User users) {
        return u_service.createUser(users);
    }

    // Getting the details of the user

    @GetMapping("/getuserinfo")

    public List<User> getUserList() {
        System.out.println(" inside User controller getUserList");
        return u_service.getUser();
    }

    @GetMapping("/getuserinfo/{user_id}")
    public User getUserbyId(@PathVariable(value = "user_id") Integer user_id) {
        return u_service.getUserbyId(user_id);
    }

    // Updating the details of an employee

    @PutMapping("/updateuserinfo/{user_id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public User updateUser(@PathVariable(value = "user_id") Integer user_id, @RequestBody User users) {
        System.out.println("inside Usercontrollers update user info " + user_id);
        return u_service.updateUser(user_id, users);

        /*
         * Authentication authentication =
         * SecurityContextHolder.getContext().getAuthentication();
         * User authenticatedUser = (User) authentication.getPrincipal();
         * if (authenticatedUser.getRole().toString() == "ADMIN") {
         * System.out.println("inside Usercontrollers java printinh user id" + user_id);
         * return u_service.updateUser(user_id, users);
         * } else {
         * System.out.
         * println(" inside usercontroller update user but role is not same as admin");
         * return null;
         * }
         */

    }

    @DeleteMapping("/deleteuserinfo/{user_id}")
    @PreAuthorize("hasAuthority('admin:delete')")

    public void deleteUserinfo(@PathVariable(value = "user_id") Integer user_id) {
        u_service.deleteUser(user_id);
    }
}
