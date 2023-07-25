package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.BonusType;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.entity.UserBonus;
import org.dtf202.subscriberservice.entity.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBonusRepository extends JpaRepository<UserBonus, Long> {

    Optional<UserBonus> findAllByUserAndBonusType(User user, BonusType bonusType);

    Optional<List<UserBonus>> findAllByUser(User user);

}
