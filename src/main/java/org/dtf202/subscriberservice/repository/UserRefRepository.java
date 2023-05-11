package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.entity.Ref;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.entity.UserRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRefRepository extends JpaRepository<UserRef, Long> {

    List<UserRef> findAllByRef(Ref ref);

    Optional<UserRef> findAllByRefAndUser(Ref ref,User user);
    Optional<UserRef> findAllByUser(User user);

    Optional<UserRef> findAllByUserAndLevel(User user,Integer level);
    @Query("Select count(*) from UserRef userRef Where userRef.ref.id = ?1 and userRef.level = 1" )
    Integer getCountOfRef(Long refId);

}
