package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.Assets;
import org.dtf202.subscriberservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

public interface AssetsRepository extends JpaRepository<Assets,Long> {
    List<Assets> findAllByPaymentTypeType(String type);
    @Query("SELECT assets From Assets assets where assets.paymentType.type = ?2 and assets.user.id = ?1")
    List<Assets> findAllByUserIdAndPaymentTypeType(Long id,String type);
    @Query("SELECT assets From Assets assets where assets.paymentType.id = ?1 and not assets.isAccepted")
    List<Assets> findAllByIsNotAccepted(Integer id);
    @Query("SELECT sum(assets.amount) From Assets assets where assets.dateTime >= ?1 and assets.paymentType.id =1 and assets.user = ?2")
    Optional<Double> findAllByDateTimeGreaterThanEqualAndPaymentTypeType(LocalDateTime dateTime, User user);

    @Query("SELECT assets From Assets assets where assets.paymentType.id in (1, 2) and not assets.isAccepted " +
            "and (" +
            "(LOWER(assets.user.firstName) LIKE CONCAT('%', :firstName, '%'))" +
            "OR (LOWER(assets.user.lastName) LIKE CONCAT('%', :lastName, '%'))" +
            "OR (LOWER(assets.user.email) LIKE CONCAT('%', :email, '%'))" +
            ")"
    )
    Page<Assets> findAllNotAcceptedAssets(Pageable pageable, @Param("firstName") String firstName,
                                        @Param("lastName") String lastName, @Param("email") String email);

    @Query("SELECT assets From Assets assets where assets.paymentType.id in (3, 4) and assets.isAccepted and assets.user = ?1")
    List<Assets> findAllActivityIncome(User user);

}
