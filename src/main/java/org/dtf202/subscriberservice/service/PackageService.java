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
    public void createUserPackage(int id, String token) throws Exception {

        Package pkg = packageRepository.findById(id).orElseThrow(() -> new Exception("Package not found"));
        User user = userRepository.findByEmail(jwtService.extractUsername(token))
            .orElseThrow(() -> new Exception("User not found"));

        if(pkg.getPrice() > user.getTotalBalance()) {
            throw new Exception("Insufficient Balance");
        }

        user.setTotalBalance(user.getTotalBalance() - pkg.getPrice());
        userRepository.save(user);

        UserRef ur = userRefRepository.findAllByUser(user).orElseThrow(() -> new Exception("User ref not found"));

        if(!ur.getRef().getIsActive()){
            if(user.getParentRef() != null) {
                Optional<Ref> ref1 = refRepository.findById(user.getParentRef());
                if(ref1.isPresent()){
                    UserRef userRef1 = userRefRepository.findAllByRefAndUser(ref1.get(),user).get();
                    UserRef userRefLevel1 = UserRef.builder().user(user).ref(ref1.get()).level(1).build();
                    userRefRepository.save(userRefLevel1);
                    Optional<UserRef> userRef2 = userRefRepository.findAllByUserAndLevel(userRef1.getUser(),1);
                    if (userRef2.isPresent()){
                        UserRef userRefLevel2 =UserRef.builder().user(user).ref(userRef2.get().getRef()).level(2).build();
                        userRefRepository.save(userRefLevel2);
                        Optional<UserRef> userRef3 = userRefRepository.findAllByUserAndLevel(userRef2.get().getUser(),1);
                        userRef3.ifPresent(userRef -> UserRef.builder().user(user).ref(userRef.getRef()).level(3).build());
                    }}
            }
            ur.getRef().setIsActive(true);
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
