package com.ethbookdapp.ethbook.controllers;

import com.ethbookdapp.ethbook.dao.UserRepository;
import com.ethbookdapp.ethbook.models.Address;
import com.ethbookdapp.ethbook.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(value = "/index")
    public ResponseEntity<User> index() {
        User user = new User();
        user.setName("Md masdud akrim");
        user.setAddress(new Address());
        user.setPassword("sdfsdfsd");

        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/login")
    public String login() {
        return "Login page";
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = this.userRepository.findAll();

        for (User user : userList) {
            this.logger.info(user.getName());
        }

        return ResponseEntity.ok(userList);
    }
}
