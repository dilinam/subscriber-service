package org.dtf202.subscriberservice.controller;

import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.AppConfig;
import org.dtf202.subscriberservice.service.AdminActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app-config")
public class AppConfigController {

    private final AdminActionService adminActionService;

    @GetMapping("/{property}")
    public ResponseEntity<AppConfig> getAppConfig(@PathVariable String property) {
        try {
            return ResponseEntity.ok(adminActionService.getAppConfig(property));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
