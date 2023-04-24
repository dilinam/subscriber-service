package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.Ref;
import org.dtf202.subscriberservice.entity.UserRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefRepository extends JpaRepository<Ref, Long> {
}
