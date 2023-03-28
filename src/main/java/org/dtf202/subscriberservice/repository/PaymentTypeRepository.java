package org.dtf202.subscriberservice.repository;

import java.util.Optional;
import org.dtf202.subscriberservice.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {

    Optional<PaymentType> findByType(String type);
}
