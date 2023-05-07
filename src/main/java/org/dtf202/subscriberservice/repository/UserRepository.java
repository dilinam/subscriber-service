package org.dtf202.subscriberservice.repository;

import java.util.Optional;
import org.dtf202.subscriberservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.isDeleted=false AND user.role.name = 'USER' AND " +
        "(LOWER(user.firstName) LIKE CONCAT('%', :firstName, '%') OR LOWER(user.lastName) LIKE CONCAT('%', :lastName, '%')" +
        " OR LOWER(user.email) LIKE CONCAT('%', :email, '%'))")
    Page<User> findAllUsers
        (Pageable pageable, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email);

}
