package org.dtf202.subscriberservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.config.JwtService;
import org.dtf202.subscriberservice.entity.*;
import org.dtf202.subscriberservice.entity.Package;
import org.dtf202.subscriberservice.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;
    private final UserPackageRepository userPackageRepository;
    private final UserRepository userRepository;
    private final UserRefRepository userRefRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final JwtService jwtService;
    private final RefRepository refRepository;
    private final BonusTypeRepository bonusTypeRepository;
    private final UserBonusRepository userBonusRepository;

    public List<Package> getAllPackages() {
        return packageRepository.findAllByIsActive(true);
    }

    public Package getPackageById(int id) throws Exception {
        return packageRepository.findById(id).orElseThrow(() -> new Exception("Package not found"));
    }
    @Transactional
    public void createUserPackage(int id,User user) throws Exception {

        Package pkg = packageRepository.findById(id).orElseThrow(() -> new Exception("Package not found"));
        if(pkg.getPrice() > user.getTotalBalance()) {
            throw new Exception("Insufficient Balance");
        }
        Optional<UserPackage> uPack = userPackageRepository.findAllByUserAndStatusIsTrue(user);
        if (uPack.isPresent()){
            uPack.get().setStatus(false);
            user.setTotalBalance(user.getTotalBalance() - pkg.getPrice() + uPack.get().getActivePackage().getPrice());
            userPackageRepository.save(uPack.get());
            userRepository.save(user);
        }else{
            user.setTotalBalance(user.getTotalBalance() - pkg.getPrice());
            userRepository.save(user);
        }


        //setRefToUsers


        UserPackage userPackage = UserPackage.builder()
            .activePackage(pkg)
            .user(user)
            .status(true)
            .startDateTime(LocalDateTime.now())
            .expireDateTime(LocalDateTime.now().plusYears(1))
            .build();
        userPackageRepository.save(userPackage);
    }
}
