package dasturlash.uz.repository;

import dasturlash.uz.entity.EmailVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository
        extends JpaRepository<EmailVerificationEntity, Integer> {

    Optional<EmailVerificationEntity> findByProfileIdAndUsedFalse(Integer profileId);

}