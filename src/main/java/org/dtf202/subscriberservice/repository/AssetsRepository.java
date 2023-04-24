package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.Assets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AssetsRepository extends JpaRepository<Assets,Long> {
    List<Assets> findAllByPaymentTypeType(String type);

    List<Assets> findAllByUserIdAndPaymentTypeType(Long id,String type);
    @Query("SELECT Assets From Assets assets where assets.paymentType.type = ?1 and not assets.isAccepted")
    List<Assets> findAllByIsNotAccepted(String type);

    List<Assets> findAllByDateTimeGreaterThanEqualAndPaymentTypeType(LocalDateTime dateTime,String type);

}
