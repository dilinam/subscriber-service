package org.dtf202.subscriberservice.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.config.JwtService;
import org.dtf202.subscriberservice.dto.AuthenticationRequest;
import org.dtf202.subscriberservice.dto.AuthenticationResponse;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.entity.UserRef;
import org.dtf202.subscriberservice.repository.RoleRepository;
import org.dtf202.subscriberservice.repository.UserRefRepository;
import org.dtf202.subscriberservice.repository.UserRepository;
import org.dtf202.subscriberservice.utils.StringHelpers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRefRepository userRefRepository;
    private final StringHelpers stringHelpers;

    @Value("${spring.mail.noreply}")
    private String noReplyEMail;

    @Value("${app.domain}")
    private String appDomain;

    private final static Map<String, User> UNVERIFIED_USERS = new HashMap<>();

    public void register(User user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("USER").orElse(null));
        user.setIsDeleted(false);
        user.setIsActive(true);
        user.setRegisteredDateTime(LocalDateTime.now());
        user.setTotalBalance(0.0);
        String verificationToken = stringHelpers.generateRandomStringUsingEmail(user.getEmail());
        UNVERIFIED_USERS.put(verificationToken, user);

        // send activation link
        Thread emailThread = new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(noReplyEMail);
            message.setTo(user.getEmail());
            message.setSubject("Dream the Future | Email Verification");
            message.setText(
                "Use the following link to Verify your email. \n\n" +
                    appDomain + "/email-verification/" + verificationToken

            );
            emailSender.send(message);
        });
        emailThread.start();
    }

    public AuthenticationResponse verifyUserAndCreate(String verificationToken) throws Exception {
        User user = UNVERIFIED_USERS.remove(verificationToken);

        if(user == null) {
            throw new Exception("Invalid Token");
        }

        userRepository.save(user);

        // create ref id for user
        String ref = stringHelpers.generateRandomStringUsingEmail(user.getEmail());
        UserRef userRef = UserRef.builder().user(user).ref(ref).build();
        userRefRepository.save(userRef);

        // generate token
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
            )
        );

        // generate token
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
