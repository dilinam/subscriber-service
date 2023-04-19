package org.dtf202.subscriberservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.Assets;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.service.AssetsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Assets")
public class AssetController {
    private final AssetsService assetsService;

    @GetMapping("/Recharges")
    public ResponseEntity<List<Assets>> getAllRecharges() {
        try {
            return ResponseEntity.ok(assetsService.getAllRecharges());
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/Withdrawals")
    public ResponseEntity<List<Assets>> getAllWithdrawals() {
        try {
            return ResponseEntity.ok(assetsService.getAllWithdrawals());
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/Recharges/{id}")
    public ResponseEntity<List<Assets>> getAllRechargesByUserId(@PathVariable long id) {
        try {
            return ResponseEntity.ok(assetsService.getAllRechargesByUserId(id));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/Withdrawals/{id}")
    public ResponseEntity<List<Assets>> getAllWithdrawalsByUserId(@PathVariable long id) {
        try {
            return ResponseEntity.ok(assetsService.getAllWithdrawalsByUserId(id));
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
    @GetMapping("/Revenue/{date}")
    public ResponseEntity<List<Assets>> getAllRevenue(@PathVariable LocalDateTime date) {
        try {
            return ResponseEntity.ok(assetsService.getAllRevenueByDate(date));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/{idT}")
    public ResponseEntity<?> AddNewAsset(@Valid @RequestBody Assets asset, @PathVariable Long id,@PathVariable Integer idT ){
        try {
            assetsService.save(asset,id,idT);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
