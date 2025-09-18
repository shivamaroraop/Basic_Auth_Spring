package com.securelogin.login.Controllers.HealthCheck;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class healthCheck {

    @GetMapping("/api/v1/signup")
    private String healthcheck(){
        return "It is working fine";
    }
}
