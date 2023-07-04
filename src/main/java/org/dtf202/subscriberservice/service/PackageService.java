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
    private final AssetsRepository assetsRepository;

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
            user.setMaximumRevenue(pkg.getPrice()*4);
            userPackageRepository.save(uPack.get());
            userRepository.save(user);
        }else{
            user.setTotalBalance(user.getTotalBalance() - pkg.getPrice());
            user.setMaximumRevenue(pkg.getPrice()*4);
            userRepository.save(user);
        }


        Optional<List<UserRef>> userRefList = userRefRepository.findAllByUser(user);
        if(userRefList.isPresent()) {
            for (UserRef uf : userRefList.get()) {
                if(uf.getLevel() != 0){
                    uf.setIsActive(true);
                }
                User user1 = userRefRepository.findUserByRef(uf.getRef());
                Assets assets;
                if (uf.getLevel() == 1) {
                    assets = Assets.builder().dateTime(LocalDateTime.now()).isAccepted(true).user(user1).amount(pkg.getPrice() * 0.1).paymentType(paymentTypeRepository.findById(3).get()).build();

                    if (user1.getMaximumRevenue() > (user1.getTotalRevenue() + assets.getAmount())) {
                        assetsRepository.save(assets);
                        user1.setTotalBalance(user1.getTotalBalance() + assets.getAmount());
                        user1.setTotalRevenue(user1.getTotalRevenue() + assets.getAmount());
                    }
                    userRepository.save(user1);

                } else if (uf.getLevel() == 2) {
                    assets = Assets.builder().dateTime(LocalDateTime.now()).isAccepted(true).user(user1).amount(pkg.getPrice() * 0.06).paymentType(paymentTypeRepository.findById(3).get()).build();

                    if (user1.getMaximumRevenue() > (user1.getTotalRevenue() + assets.getAmount())) {
                        assetsRepository.save(assets);
                        user1.setTotalBalance(user1.getTotalBalance() + assets.getAmount());
                        user1.setTotalRevenue(user1.getTotalRevenue() + assets.getAmount());
                    }
                    userRepository.save(user1);
                } else if (uf.getLevel() == 3) {
                    assets = Assets.builder().dateTime(LocalDateTime.now()).isAccepted(true).user(user1).amount(pkg.getPrice() * 0.03).paymentType(paymentTypeRepository.findById(3).get()).build();

                    if (user1.getMaximumRevenue() > (user1.getTotalRevenue() + assets.getAmount())) {
                        assetsRepository.save(assets);
                        user1.setTotalBalance(user1.getTotalBalance() + assets.getAmount());
                        user1.setTotalRevenue(user1.getTotalRevenue() + assets.getAmount());
                    }
                    userRepository.save(user1);
                }
                userRefRepository.save(uf);
            }
        }
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
