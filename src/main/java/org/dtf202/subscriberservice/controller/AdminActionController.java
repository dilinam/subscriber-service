package org.dtf202.subscriberservice.controller;

import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.AppConfig;
import org.dtf202.subscriberservice.entity.Assets;
import org.dtf202.subscriberservice.service.AdminActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adminAction")
public class AdminActionController {
    private final AdminActionService adminActionService;
    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            adminActionService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> acceptUserRequestId(@Valid @RequestBody Assets asset) {
        try {
            adminActionService.acceptUserRequest(asset);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/all")
    public ResponseEntity<?> acceptUserRequestId(@Valid @RequestBody List<Long> userList) {
        try {
            adminActionService.acceptAllusers(userList);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/notAcceptedRecharges")
    public ResponseEntity<List<Assets>> allNotAcceptedRecharges(){
        try {
            return ResponseEntity.ok(adminActionService.getAllNotAcceptedRecharges());
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/notAcceptedWithdrawals")
    public ResponseEntity<List<Assets>> allNotAcceptedWithdrawals(){
        try {
            return ResponseEntity.ok(adminActionService.getAllNotAcceptedWithdrawals());
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getAllNotAcceptedAssets")
    public ResponseEntity<Map<String, Object>> getAllNotAcceptedAssets(@RequestParam int pageNumber, @RequestParam int pageSize,
                                                                       @RequestParam String globalFilter){
        return ResponseEntity.ok(adminActionService.getAllNotAcceptedAssets(pageNumber, pageSize, globalFilter));
    }
    @PutMapping("/reject-asset")
    public ResponseEntity<?> rejectAsset(@Valid @RequestBody Assets asset) {
        try {
            adminActionService.rejectAsset(asset);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/accept-asset")
    public ResponseEntity<?> acceptAsset(@Valid @RequestBody Assets asset) {
        try {
            adminActionService.acceptAsset(asset);
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/app-config")
    public void saveAppConfig(@RequestBody AppConfig appConfig) {
        adminActionService.saveAppConfig(appConfig);
    }

}
