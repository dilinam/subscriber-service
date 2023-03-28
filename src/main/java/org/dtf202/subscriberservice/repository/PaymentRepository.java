package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
