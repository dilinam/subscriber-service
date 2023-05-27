package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.Ref;
import org.dtf202.subscriberservice.entity.RevenueUserPackage;
import org.dtf202.subscriberservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RevenueUserPackageRepository extends JpaRepository<RevenueUserPackage, Integer> {

    List<RevenueUserPackage> findAllByUserAndDateTimeGreaterThanEqual(User user, LocalDateTime timestamp);

    @Query("Select sum(rup.revenue) from RevenueUserPackage rup where rup.user = ?1")
    Double findSumofRev(User user);
}
