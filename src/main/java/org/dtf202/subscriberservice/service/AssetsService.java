package org.dtf202.subscriberservice.service;

import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.*;
import org.dtf202.subscriberservice.repository.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;


@Service
@RequiredArgsConstructor
public class AssetsService {
    private final UserRepository userRepository;
    private final AssetsRepository assetsRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final RevenueUserPackageRepository revenueUserPackageRepository;
    private final UserBonusRepository userBonusRepository;
    private final BonusTypeRepository bonusTypeRepository;
    public List<Assets> getAllRecharges(){
        return assetsRepository.findAllByPaymentTypeType("Recharge");

    }
    public List<Assets> getAllActivityIncome(User user){
        return assetsRepository.findAllActivityIncome(user);

    }

    public List<Assets> getAllRechargesByUserId(Long id){
        return assetsRepository.findAllByUserIdAndPaymentTypeType(id,"Recharge");

    }
    public List<Assets> getAllWithdrawalsByUserId(Long id){
        return assetsRepository.findAllByUserIdAndPaymentTypeType(id,"Withdrawal");

    }
    public List<RevenueUserPackage> getAllRevenueByDate(long startTimestamp, long endTimestamp,User user){
        LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTimestamp), TimeZone.getDefault().toZoneId());
        LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endTimestamp), TimeZone.getDefault().toZoneId());

        List<RevenueUserPackage> rev = revenueUserPackageRepository.findAllByUserAndDateTimeBetween(user,startLocalDateTime, endLocalDateTime);

        return rev;

    }
    public List<Assets> getAllRefByUser(Long id){
        return assetsRepository.findAllByUserIdAndPaymentTypeType(id,"RefCom");

    }

    public List<Assets> getAllRevenueByUser(Long id){
        return assetsRepository.findAllByUserIdAndPaymentTypeType(id,"Revenue");

    }

    public void save(User user,Integer id,Double amount){
        Optional<PaymentType> paymentType = paymentTypeRepository.findById(id);
        Assets assets;
        if (id == 1 && paymentType.isPresent()){
            assets = Assets.builder().dateTime(LocalDateTime.now()).isAccepted(false).user(user).amount(-amount).paymentType(paymentType.get()).build();
        }else{
            assets = Assets.builder().dateTime(LocalDateTime.now()).isAccepted(false).user(user).amount(amount).paymentType(paymentType.get()).build();
        }

        assetsRepository.save(assets);
    }
    public void saveBonus (User user,Double amount){
        Assets assets = Assets.builder().dateTime(LocalDateTime.now()).isAccepted(true).user(user).amount(amount).paymentType(paymentTypeRepository.findById(4).get()).build();

        if(user.getMaximumRevenue()*2 > (user.getTotalRevenue()+assets.getAmount())){
            assetsRepository.save(assets);
            user.setTotalBalance(user.getTotalBalance()+assets.getAmount());
            user.setTotalRevenue(user.getTotalRevenue()+assets.getAmount());
            UserBonus userBonus = UserBonus.builder().bonusType(bonusTypeRepository.findBonusTypeByPrice(amount)).user(user).build();
            userBonusRepository.save(userBonus);
        }
        userRepository.save(user);
    }


}
