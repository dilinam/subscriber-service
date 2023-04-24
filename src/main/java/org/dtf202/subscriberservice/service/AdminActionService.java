package org.dtf202.subscriberservice.service;

import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.Assets;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.repository.AssetsRepository;
import org.dtf202.subscriberservice.repository.PaymentTypeRepository;
import org.dtf202.subscriberservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminActionService {
    private final UserRepository userRepository;
    private final AssetsRepository assetsRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    public List<Assets> getAllNotAcceptedRecharges(){
        return assetsRepository.findAllByIsNotAccepted("Recharge");
    }

    public List<Assets> getAllNotAcceptedWithdrawals(){
        return assetsRepository.findAllByIsNotAccepted("Withdrawal");
    }
    public void acceptUserRequest(Assets asset){
        asset.setIsAccepted(true);
        Double balance = asset.getUser().getTotalBalance();
        Double totalBalance = balance + asset.getAmount();
        asset.getUser().setTotalBalance(totalBalance);
        assetsRepository.save(asset);
    }
    @Transactional
    public void deleteUser(Long id){
        Optional<User> user = userRepository.findById(id);
        user.get().setIsDeleted(true);
        userRepository.save(user.get());
    }

    public void acceptAllusers(List<Long> idList){
        for (Long id:idList) {
            Optional<Assets> assets =  assetsRepository.findById(id);
            assets.ifPresent(value -> value.setIsAccepted(true));
        }
    }

}
