package org.dtf202.subscriberservice.controller;

import jakarta.validation.Valid;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.entity.UserPackage;
import org.dtf202.subscriberservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Map<String, Object> users(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String globalFilter) {
        return userService.getAllUsers(pageNumber, pageSize, globalFilter.toLowerCase());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable  long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/package")
    public ResponseEntity<UserPackage> getUserPackageById( Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.getUserPackageById(user));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> editUser(@Valid @RequestBody User editingUser) {
        try {
            userService.editUser(editingUser);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<?> changeUserState(@PathVariable long id) {
        try {
            userService.changeUserStatus(id);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/totalRev/{id}")
    public ResponseEntity<Double> getTotalBalanceRev(@PathVariable long id){
        try {
            return ResponseEntity.ok(userService.getTotalBalRev(id));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/l1count/{id}")
    public ResponseEntity<Integer> getBonusUsers(@PathVariable long id){
        try {
            return ResponseEntity.ok(userService.getBonus(id));
        } catch(Exception ex) {
            System.out.println(ex);
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getusername")
    public ResponseEntity<?> getUser(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(user);
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }


}
