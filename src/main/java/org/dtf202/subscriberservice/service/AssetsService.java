package org.dtf202.subscriberservice.service;

import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.Assets;
import org.dtf202.subscriberservice.entity.PaymentType;
import org.dtf202.subscriberservice.entity.RevenueUserPackage;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.repository.AssetsRepository;
import org.dtf202.subscriberservice.repository.PaymentTypeRepository;
import org.dtf202.subscriberservice.repository.RevenueUserPackageRepository;
import org.dtf202.subscriberservice.repository.UserRepository;
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
    public List<Assets> getAllRecharges(){
        return assetsRepository.findAllByPaymentTypeType("Recharge");

    }
    public List<Assets> getAllWithdrawals(){
        return assetsRepository.findAllByPaymentTypeType("Withdrawal");

    }
    public List<Assets> getAllRechargesByUserId(Long id){
        return assetsRepository.findAllByUserIdAndPaymentTypeType(id,"Recharge");

    }
    public List<Assets> getAllWithdrawalsByUserId(Long id){
        return assetsRepository.findAllByUserIdAndPaymentTypeType(id,"Withdrawal");

    }
    public List<Assets> getAllNotAcceptedWithdrawals(){
        return assetsRepository.findAllByIsNotAccepted(2);
    }
    public List<Assets> getAllNotAcceptedRecharge(){
        return assetsRepository.findAllByIsNotAccepted(1);
    }
    public List<RevenueUserPackage> getAllRevenueByDate(long timestamp,User user){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());

        List<RevenueUserPackage> rev = revenueUserPackageRepository.findAllByUserAndDateTimeGreaterThanEqual(user,localDateTime);

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

}
