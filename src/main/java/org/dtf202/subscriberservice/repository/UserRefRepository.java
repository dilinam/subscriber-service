package org.dtf202.subscriberservice.repository;

import org.dtf202.subscriberservice.dto.RefCountBYLevel;
import org.dtf202.subscriberservice.entity.Ref;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.entity.UserRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRefRepository extends JpaRepository<UserRef, Long> {

    List<UserRef> findAllByRefAndIsActiveTrue(Ref ref);

    Optional<UserRef> findAllByRefAndUser(Ref ref,User user);
    Optional<UserRef> findFirstByRef(Ref ref);
    Optional<List<UserRef>> findAllByUser(User user);

    Optional<UserRef> findAllByUserAndLevel(User user,Integer level);

    Optional<List<UserRef>> findAllByRefAndLevel(User user,Integer level);
    @Query("Select count(*) from UserRef userRef Where userRef.ref.id = ?1 and userRef.level = ?2 and userRef.isActive = true " )
    Integer getCountOfRef(Long refId,Integer level);

    List<UserRef> findAllByRefAndLevelAndIsActiveTrue(Ref ref,Integer level);

    @Query("Select userRef.user from UserRef userRef where userRef.ref = ?1 and userRef.level = 0")
    User findUserByRef(Ref ref);
    @Query("Select COUNT(*) from UserRef userRef Where userRef.user.registeredDateTime > ?1 and userRef.level = ?2 and userRef.ref.id = ?3" )
    Integer findUserREfByDateAndLevelAndCount(LocalDateTime date,Integer Level,Long refId);

    @Query("Select COUNT(*) from UserRef userRef, UserPackage up Where userRef.user.id = up.user.id and userRef.level = ?2 and up.startDateTime > ?1 and userRef.ref.id = ?3 " )
    Integer findUserREfByDateAndLevelAndCountPackage(LocalDateTime date,Integer Level,Long refId);

}
