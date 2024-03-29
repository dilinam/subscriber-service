package org.dtf202.subscriberservice.controller;

import jakarta.validation.Valid;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.dto.RefCountBYLevel;
import org.dtf202.subscriberservice.entity.*;
import org.dtf202.subscriberservice.service.UserService;
import org.springframework.http.HttpStatus;
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
//            return ResponseEntity.notFound().build();
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> editUser(@Valid @RequestBody User editingUser) {
        try {
            userService.editUser(editingUser);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
        }            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

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
    @GetMapping("/totalRev/")
    public ResponseEntity<Double> getTotalBalanceRev(Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.getTotalBalRev(user));
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
    @GetMapping("/getreflink")
    public ResponseEntity<?> getRef(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.getRefID(user));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getAllRef")
    public ResponseEntity<List<UserRef>> getAllRef(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.getRefUsers(user));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getCountRef/{level}")
    public ResponseEntity<Integer> getCountRef(Authentication authentication,@PathVariable Integer level) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.getCountRef(user,level));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getuserBonus")
    public ResponseEntity<List<UserBonus>> getUserBonus(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.getuserBouns(user));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getlevelUserRef/{level}")
    public ResponseEntity<List<UserRef>> getlevelUserRef(Authentication authentication,@PathVariable Integer level) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.getAllUserRefBylevel(user,level));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getCountUserLevelBydate/{level}")
    public Integer getCountUserLevelBydate(Authentication authentication ,@PathVariable Integer level){
        User user = (User) authentication.getPrincipal();
        return userService.getCountUserLevel(user,level);
    }

    @GetMapping("/getCountNewPackageActive/{level}")
    public Integer getCountNewPackageActive(Authentication authentication ,@PathVariable Integer level){
        User user = (User) authentication.getPrincipal();
        return userService.getCountUserLevelPackage(user,level);
    }

    @GetMapping("/getCardDeatils")
    public ResponseEntity<CardMgt>getCardDeatils(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.getCardDetailsUser(user));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getCardDetailsByUser/{userId}")
    public ResponseEntity<CardMgt>getCardDetailsByUser(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(userService.getCardDetailsUser(userService.getUserById(userId)));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/putCardDatails")
    public ResponseEntity<?> putCardDatails(@RequestBody CardMgt cardMgt,Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            cardMgt.setUser(user);
            userService.saveCard(cardMgt);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getAllWithdrawalBYDate/{level}")
    public ResponseEntity<Double> getAllEithdrawallByDate(@PathVariable int level,Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.getAllWithdrawalsByDate(level,user));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }



}
