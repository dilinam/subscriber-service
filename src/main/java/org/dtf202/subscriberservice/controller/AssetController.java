package org.dtf202.subscriberservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.Assets;
import org.dtf202.subscriberservice.entity.RevenueUserPackage;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.service.AssetsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Assets")
public class AssetController {
    private final AssetsService assetsService;

    @GetMapping("/ActivityIncome")
    public ResponseEntity<List<Assets>> getAllWithdrawalByUserId(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(assetsService.getAllActivityIncome(user));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/Recharge")
    public ResponseEntity<List<Assets>> getAllRechargesByUserId(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(assetsService.getAllRechargesByUserId(user.getId()));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/Withdrawals")
    public ResponseEntity<List<Assets>> getAllWithdrawalsByUserId(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(assetsService.getAllWithdrawalsByUserId(user.getId()));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/RefCom/{id}")
    public ResponseEntity<List<Assets>> getAllRef(@PathVariable long id) {
        try {

            return ResponseEntity.ok(assetsService.getAllRefByUser(id));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/Revenue/user/{id}")
    public ResponseEntity<List<Assets>> getAllRevenueUser(@PathVariable long id) {
        try {
            return ResponseEntity.ok(assetsService.getAllRevenueByUser(id));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Revenue/{startTimestamp}/{endTimestamp}")
    public ResponseEntity<List<RevenueUserPackage>> getAllRevenue(@PathVariable long startTimestamp, @PathVariable long endTimestamp,
                                                                  Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(assetsService.getAllRevenueByDate(startTimestamp, endTimestamp,user));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/{amount}")
    public ResponseEntity<?> AddNewAsset(@Valid @PathVariable Integer id,@PathVariable Double amount,Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            assetsService.save(user,id,amount);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            System.out.println(ex);
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/Bonus/{amount}")
    public ResponseEntity<?> AddNewAsset(@Valid @PathVariable Double amount,Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            assetsService.saveBonus(user,amount);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            System.out.println(ex);
            return ResponseEntity.notFound().build();
        }
    }
}
