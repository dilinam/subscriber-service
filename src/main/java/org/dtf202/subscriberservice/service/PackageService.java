package org.dtf202.subscriberservice.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.config.JwtService;
import org.dtf202.subscriberservice.entity.Package;
import org.dtf202.subscriberservice.entity.Payment;
import org.dtf202.subscriberservice.entity.PaymentType;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.entity.UserPackage;
import org.dtf202.subscriberservice.repository.PackageRepository;
import org.dtf202.subscriberservice.repository.PaymentRepository;
import org.dtf202.subscriberservice.repository.PaymentTypeRepository;
import org.dtf202.subscriberservice.repository.UserPackageRepository;
import org.dtf202.subscriberservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;
    private final UserPackageRepository userPackageRepository;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentRepository paymentRepository;
    private final JwtService jwtService;

    public List<Package> getAllPackages() {
        return packageRepository.findAllByIsActive(true);
    }

    public Package getPackageById(int id) throws Exception {
        return packageRepository.findById(id).orElseThrow(() -> new Exception("Package not found"));
    }

    @Transactional
    public void createUserPackage(int id, String token) throws Exception {

        Package pkg = packageRepository.findById(id).orElseThrow(() -> new Exception("Package not found"));
        User user = userRepository.findByEmail(jwtService.extractUsername(token))
            .orElseThrow(() -> new Exception("User not found"));

        if(pkg.getPrice() > user.getTotalBalance()) {
            throw new Exception("Insufficient Balance");
        }

        // set payment details
        PaymentType paymentType = paymentTypeRepository.findByType("SUBSCRIBE")
            .orElseThrow(() -> new Exception("Payment Type not found"));
        Payment payment = Payment.builder()
            .paymentType(paymentType)
            .user(user)
            .amount(pkg.getPrice())
            .dateTime(LocalDateTime.now())
            .isAccepted(true)
            .build();
        paymentRepository.save(payment);
        user.setTotalBalance(user.getTotalBalance() - pkg.getPrice());
        userRepository.save(user);

        UserPackage userPackage = UserPackage.builder()
            .activePackage(pkg)
            .user(user)
            .status(true)
            .startDateTime(LocalDateTime.now())
            .expireDateTime(LocalDateTime.now().plusYears(1))
            .payment(payment)
            .build();
        userPackageRepository.save(userPackage);
    }
}
