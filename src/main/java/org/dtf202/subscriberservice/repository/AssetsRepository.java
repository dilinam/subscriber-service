package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.Assets;
import org.dtf202.subscriberservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AssetsRepository extends JpaRepository<Assets,Long> {
    List<Assets> findAllByPaymentTypeType(String type);

    List<Assets> findAllByUserIdAndPaymentTypeType(Long id,String type);
    @Query("SELECT assets From Assets assets where assets.paymentType.id = ?1 and not assets.isAccepted")
    List<Assets> findAllByIsNotAccepted(Integer id);
    @Query("SELECT assets From Assets assets where assets.dateTime >= ?1 and assets.paymentType.type = ?2")
    List<Assets> findAllByDateTimeGreaterThanEqualAndPaymentTypeType(LocalDateTime dateTime,String type);
    @Query("Select sum(assets.amount) from Assets assets where assets.user = ?1 and assets.paymentType.id = 3")
    Double findSumofRev(User user);

}
