package org.dtf202.subscriberservice.controller;

import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.AppConfig;
import org.dtf202.subscriberservice.service.AdminActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/{value}")
    public void saveAppConfig(@PathVariable String value) throws Exception {
        System.out.println("...");
        AppConfig appConfig = adminActionService.getAppConfig("DISABLE_REG_AND_NEW_PKG");
        appConfig.setValue(value);
        adminActionService.saveAppConfig(appConfig);
    }

}
