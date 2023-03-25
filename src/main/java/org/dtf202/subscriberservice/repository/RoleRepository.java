package org.dtf202.subscriberservice.repository;

import java.util.Optional;
import org.dtf202.subscriberservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
