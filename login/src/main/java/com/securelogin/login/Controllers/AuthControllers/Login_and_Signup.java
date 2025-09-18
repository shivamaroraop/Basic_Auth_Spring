package com.securelogin.login.Controllers.AuthControllers;

import com.securelogin.login.Dtos.LoginDto;
import com.securelogin.login.Entities.User;
import com.securelogin.login.Services.AuthServices.AuthPasswordMatchingService;
import com.securelogin.login.Services.AuthServices.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin
public class Login_and_Signup {

    private AuthService authService;
    private AuthPasswordMatchingService authPasswordMatchingService;

    @PostMapping("/signup/1")
    private ResponseEntity<User> signup(@RequestBody User user) {
       try{ authService.signup(user);
       return ResponseEntity.ok(user);
       }
       catch(Exception e){
           System.out.println(e);
           return ResponseEntity.badRequest().build();
       }
    }

    @PostMapping("/login/1")
    private ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try{
            authPasswordMatchingService.authenticate(loginDto.getUsername(), loginDto.getPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
