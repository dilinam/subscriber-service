package org.dtf202.subscriberservice.repository;

import java.util.Optional;
import org.dtf202.subscriberservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
