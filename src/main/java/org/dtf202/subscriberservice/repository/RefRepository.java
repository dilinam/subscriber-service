package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.Ref;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.entity.UserRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefRepository extends JpaRepository<Ref, Long> {
    @Query("Select max(ref.id) from Ref ref")
    Long maxID();
}
