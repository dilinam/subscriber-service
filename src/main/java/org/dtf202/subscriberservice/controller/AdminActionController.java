package org.dtf202.subscriberservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.service.AdminActionService;
import org.dtf202.subscriberservice.service.AssetsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adminAction")
public class AdminActionController {
    private final AdminActionService adminActionService;
    @PutMapping
    public ResponseEntity<?> editUser(@Valid @RequestBody User editingUser) {
        try {
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
