package com.securelogin.login.Services.AuthServices;

import com.securelogin.login.Entities.Role;
import com.securelogin.login.Entities.User;
import com.securelogin.login.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String signup(User user) {
        if (userRepository.existsByUsername((user.getUsername()))){
            return "Username already exists!";
        }

        if (userRepository.existsByEmail((user.getEmail()))){
            return "Email already exists!";
        }

        User newUser = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
        userRepository.save(newUser);
        return "User registered successfully!";
    }
}
