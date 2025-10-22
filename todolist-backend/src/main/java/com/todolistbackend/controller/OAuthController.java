package com.todolistbackend.controller;

import com.todolistbackend.OAuth.dto.OAuthUserDto;
import com.todolistbackend.OAuth.service.OAuthServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/oauth")
@CrossOrigin("*")
public class OAuthController {

    private final OAuthServiceImp oAuthServiceImp;

    public OAuthController(OAuthServiceImp oAuthServiceImp) {
        this.oAuthServiceImp = oAuthServiceImp;
    }


    // Google: frontend sends idToken

}
