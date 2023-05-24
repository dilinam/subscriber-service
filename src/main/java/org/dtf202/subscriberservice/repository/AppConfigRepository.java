package org.dtf202.subscriberservice.repository;

import java.util.Optional;
import org.dtf202.subscriberservice.entity.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {
    Optional<AppConfig> findByProperty(String property);
}
