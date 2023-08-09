package org.dtf202.subscriberservice.controller;

import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.dto.AuthenticationRequest;
import org.dtf202.subscriberservice.dto.AuthenticationResponse;
import org.dtf202.subscriberservice.entity.AppConfig;
import org.dtf202.subscriberservice.entity.Package;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.service.AdminActionService;
import org.dtf202.subscriberservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {

        try{
            authService.register(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("email", "Email exists"));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("userRef", "User ref not exists"));
        }
    }

    @PostMapping("/email-verification/{verificationToken}")
    public ResponseEntity<AuthenticationResponse> emailVerification(@PathVariable String verificationToken) {

        try {
            return ResponseEntity.ok(authService.verifyUserAndCreate(verificationToken));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }
    @PostMapping("/resetPasswordVerify/{email}")
    public ResponseEntity<?> resetPassword(@PathVariable String email) {
        try{
            authService.resetPasswordVerify(email);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("email", "Email doesn't exists"));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("userRef", "User ref not exists"));
        }
    }
    @PostMapping("/reset-password/{verificationToken}")
    public ResponseEntity<AuthenticationResponse> emailVerification(@PathVariable String verificationToken,@Valid @RequestBody String password) {

        try {
            authService.resetPassword(verificationToken,password);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/countOfRef")
    public ResponseEntity<Long> getCountOfRef() {
        try {
            return ResponseEntity.ok(authService.getMaxID());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
