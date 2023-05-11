package org.dtf202.subscriberservice.repository;

import java.util.List;
import org.dtf202.subscriberservice.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Integer> {
    List<Package> findAllByIsActive(boolean isActive);


}
