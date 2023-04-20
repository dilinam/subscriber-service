package org.dtf202.subscriberservice.service;

import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.Assets;
import org.dtf202.subscriberservice.entity.PaymentType;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.repository.AssetsRepository;
import org.dtf202.subscriberservice.repository.PaymentTypeRepository;
import org.dtf202.subscriberservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AssetsService {
    private final UserRepository userRepository;
    private final AssetsRepository assetsRepository;
    private final PaymentTypeRepository paymentTypeRepository;
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
        return assetsRepository.findAllByIsNotAccepted("Recharge");
    }
    public List<Assets> getAllNotAcceptedRecharge(){
        return assetsRepository.findAllByIsNotAccepted("Withdrawal");
    }
    public List<Assets> getAllRevenueByDate(LocalDateTime date){
        return assetsRepository.findAllByDateTimeGreaterThanEqualAndPaymentTypeType(date,"Revenue");

    }
    public List<Assets> getAllRefByUser(Long id){
        return assetsRepository.findAllByUserIdAndPaymentTypeType(id,"RefCom");

    }

    public void save(Assets assets,Long id,Integer idT){
        if (assets.getIsAccepted()){
            Optional<User> user = userRepository.findById(id);
            Optional<PaymentType> paymentType = paymentTypeRepository.findById(idT);
            assets.setUser(user.get());
            assets.setPaymentType(paymentType.get());
            assetsRepository.save(assets);
        }
    }

}
