package tech.logicforge.moneymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.logicforge.moneymanager.entity.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {

    Optional<ProfileEntity> findByEmail(String emailId);

    Optional<ProfileEntity> findByActivationToken(String activationToken);

}
