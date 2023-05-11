package org.dtf202.subscriberservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.*;
import org.dtf202.subscriberservice.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRefRepository userRefRepository;
    private final UserBonusRepository userBonusRepository;
    private final BonusTypeRepository bonusTypeRepository;
    private final AssetsRepository assetsRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final UserPackageRepository userPackageRepository;

    public Map<String, Object> getAllUsers(int pageNumber, int pageSize, String globalFilter) {
        Page<User> userPage = userRepository
            .findAllUsers
                (PageRequest.of(pageNumber, pageSize), globalFilter, globalFilter, globalFilter);
        return Map.of("data", userPage.stream().toList(), "count", userPage.getTotalElements());
    }

    public User getUserById(long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public UserPackage getUserPackageById(User user ) throws Exception {

        UserPackage userPackage = userPackageRepository.findAllByUserAndStatusIsTrue(user).orElseThrow(() -> new Exception("User not found"));
        return userPackage;
    }

    @Transactional
    public void changeUserStatus(long id) throws Exception {
        User user = getUserById(id);
        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
    }

    @Transactional
    public void editUser(User editingUser) throws Exception {
        User user = getUserById(editingUser.getId());
        user.setFirstName(editingUser.getFirstName());
        user.setLastName(editingUser.getLastName());
        userRepository.save(user);
    }
    public Double getTotalBalRev(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
        return assetsRepository.findSumofRev(user);
    }

    public Integer getBonus(Long id){
        Optional<User> user = userRepository.findById(id);
        Optional<UserRef> userRef = userRefRepository.findAllByUserAndLevel(user.get(),0);
        Ref ref = userRef.get().getRef();
        Long refId = ref.getId();
        Integer count =  userRefRepository.getCountOfRef(refId);
        if(count > 1 && userBonusRepository.findAllByUserAndBonusType(user.get(),bonusTypeRepository.findAllByType("10")).isEmpty()){
            return 100;
         }else{
            return 0;
        }

    }
    public List<UserRef> getRefUsers(User user){
        Optional<UserRef> userRef = userRefRepository.findAllByUserAndLevel(user,0);
        Ref ref = userRef.get().getRef();

        List<UserRef> userrefs= userRefRepository.findAllByRef(ref);

        return userrefs;


    }
}




