package com.example.reddit.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.example.reddit.dto.LoginRequest;
import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.dto.AuthenticationResponse;
import com.example.reddit.entities.NotificationEmail;
import com.example.reddit.entities.User;
import com.example.reddit.entities.VerificationToken;
import com.example.reddit.exceptions.SpringRedditException;
import com.example.reddit.repositories.UserRepository;
import com.example.reddit.repositories.VerificationTokenRepository;
import com.example.reddit.security.JwtProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest){
        
        User user = new User();
        
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please activate your reddit account !!!", user.getEmail(),"Thank you for signing up, " +
        "please click the url below to activate your account :" + "http://localhost:8080/api/auth/accountVerification/" + token));

    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        
        VerificationToken verificationToken = new VerificationToken();

        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        
        return token;
    }

	public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserandEnable(verificationToken.get());
	}

    @Transactional
    private void fetchUserandEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("username "+ username +" not found !!!"));
        user.setEnabled(true);
        userRepository.save(user);
    }

	public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, loginRequest.getUsername());
	}

}
