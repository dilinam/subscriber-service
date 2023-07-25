package org.dtf202.subscriberservice.repository;


import org.dtf202.subscriberservice.entity.CardMgt;
import org.dtf202.subscriberservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardMgtRepository extends JpaRepository<CardMgt, Long> {
    CardMgt findByUser(User user);
}
