package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.Package;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.entity.UserPackage;
import org.dtf202.subscriberservice.entity.UserRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {
    Optional<UserPackage> findAllByUserAndStatusIsTrue(User user);

    Optional<List<UserPackage>> findAllByStatusIsTrue();

    Optional<UserPackage> findAllByUser(User user);
}
