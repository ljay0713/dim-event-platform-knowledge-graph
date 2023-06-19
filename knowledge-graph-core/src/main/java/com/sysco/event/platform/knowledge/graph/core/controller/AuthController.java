package com.sysco.event.platform.knowledge.graph.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.sysco.event.platform.knowledge.graph.core.security.JWTAuthorizationFilter.getJWTToken;

@RestController
public class AuthController {

    @GetMapping("/token")
    public String token(@RequestParam("username") String username) {
        return getJWTToken(username);
    }

}
