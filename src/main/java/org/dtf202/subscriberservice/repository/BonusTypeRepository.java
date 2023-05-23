package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.BonusType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusTypeRepository  extends JpaRepository<BonusType, Long> {

    BonusType findAllByType(String type);
}
