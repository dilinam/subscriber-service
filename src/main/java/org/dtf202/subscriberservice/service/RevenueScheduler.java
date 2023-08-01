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

    @Scheduled(cron = "0 29 13 * * MON,TUE,WED,THU,FRI")
//    @Scheduled(cron = "1 * * * * *")
    public void revScheduler(){
        LocalDateTime dt = LocalDateTime.now();
        Optional<List<UserPackage>> userPkList= userPackageRepository.findAllByStatusIsTrue();
        if(userPkList.isPresent()){
        for (UserPackage usPk: userPkList.get()) {
            User user = usPk.getUser();
            if(user.getMaximumRevenue() > user.getTotalDailyRevenue() + usPk.getActivePackage().getPrice() * 0.005 ){
            RevenueUserPackage revenueUserPackage = RevenueUserPackage.builder().user(usPk.getUser()).aPackage(usPk.getActivePackage()).revenue(usPk.getActivePackage().getPrice() * 0.005).dateTime(LocalDateTime.now()).build();

            user.setTotalBalance(usPk.getUser().getTotalDailyRevenue() + usPk.getActivePackage().getPrice() * 0.005);
            user.setTotalDailyRevenue( usPk.getUser().getTotalDailyRevenue() + usPk.getActivePackage().getPrice() * 0.005);
            user.setTotalRevenue(usPk.getUser().getTotalRevenue()  +  usPk.getActivePackage().getPrice() * 0.005);

            revenueUserPackageRepository.save(revenueUserPackage);
            userRepository.save(user);}

        }
        }



        System.out.println("hello"+dt.now());
    }
}
