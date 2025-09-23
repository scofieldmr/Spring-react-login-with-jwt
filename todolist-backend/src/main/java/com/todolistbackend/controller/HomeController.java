package com.todolistbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/admin/home")
    public String adminHome() {
        logger.info("Fetching the Admin Home Page");
        return "Welcome to the Admin Home Page";
    }

    @GetMapping("/user/home")
    public String userHome() {
        logger.info("Fetching the User Home Page");
        return "Welcome to the User Home Page";
    }

    @GetMapping("/home")
    public String defaultHome() {
        logger.info("Fetching the Default Home Page");
        return "Welcome to the default Home Page";
    }

    @GetMapping("/mod/home")
    public String modHome() {
        logger.info("Fetching the Moderator Home Page");
        return "Welcome to the Moderator Home Page";
    }

}
