package com.example.reddit.controller;

import java.util.List;

import com.example.reddit.Service.AuthService;
import com.example.reddit.dto.AuthenticationResponse;
import com.example.reddit.dto.LoginRequest;
import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.entities.User;
import com.example.reddit.entities.VerificationToken;
import com.example.reddit.repositories.UserRepository;
import com.example.reddit.repositories.VerificationTokenRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    
    private final UserRepository userRepository;
    private final AuthService authService;
    private final VerificationTokenRepository verificationTokenRepository;

    @GetMapping("/listUsers")
    public List<User> listUsers(){
        return userRepository.findAll();

    }

    @GetMapping("/listTokens")
    public List<VerificationToken> listTokens(){
        return verificationTokenRepository.findAll();
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User registration successful !", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
       return authService.login(loginRequest);
    }
}
