package org.dtf202.subscriberservice.service;

import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.RevenueUserPackage;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.entity.UserPackage;
import org.dtf202.subscriberservice.repository.RevenueUserPackageRepository;
import org.dtf202.subscriberservice.repository.UserPackageRepository;
import org.dtf202.subscriberservice.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RevenueScheduler {
    private final RevenueUserPackageRepository revenueUserPackageRepository;
    private final UserRepository userRepository;
    private final UserPackageRepository userPackageRepository;

    @Scheduled(cron = "0 8 * * * *")
    public void revScheduler(){
        LocalDateTime dt = LocalDateTime.now();
        Optional<List<UserPackage>> userPkList= userPackageRepository.findAllByStatusIsTrue();
        if(userPkList.isPresent()){
        for (UserPackage usPk: userPkList.get()) {
            RevenueUserPackage revenueUserPackage = RevenueUserPackage.builder().user(usPk.getUser()).aPackage(usPk.getActivePackage()).revenue(usPk.getActivePackage().getPrice() * 0.01).dateTime(LocalDateTime.now()).build();
            User user = usPk.getUser();
            user.setTotalBalance(usPk.getUser().getTotalBalance() + usPk.getActivePackage().getPrice() * 0.01);

            revenueUserPackageRepository.save(revenueUserPackage);
            userRepository.save(user);

        }
        }



        System.out.println("hello"+dt.now());
    }
}
