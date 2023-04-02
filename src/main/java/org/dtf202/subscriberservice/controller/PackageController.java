package org.dtf202.subscriberservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.Package;
import org.dtf202.subscriberservice.service.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/packages")
public class PackageController {

    private final PackageService packageService;

    @GetMapping
    public List<Package> getAllPackages(){
        return packageService.getAllPackages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(packageService.getPackageById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> createUserPackage(@PathVariable int id, HttpServletRequest request) {

        try {
            String token = request.getHeader("Authorization").substring(7);
            packageService.createUserPackage(id, token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
