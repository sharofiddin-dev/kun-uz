package dasturlash.uz.service;

import dasturlash.uz.dto.auth.EmailVerificationDTO;
import dasturlash.uz.entity.EmailVerificationEntity;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.exception.AppBadException;
import dasturlash.uz.repository.EmailVerificationRepository;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private ProfileRepository profileRepository;

    // =====================================================
    // 1. Verification code ni database ga saqlash
    // =====================================================
    public void saveVerificationCode(Integer profileId, String code) {

        EmailVerificationEntity entity = new EmailVerificationEntity();

        entity.setProfileId(profileId);
        entity.setCode(code);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setExpiredDate(LocalDateTime.now().plusMinutes(2));
        entity.setUsed(false);

        emailVerificationRepository.save(entity);
    }

    // =====================================================
    // 2. Verification code ni tekshirish va confirm qilish
    // =====================================================
    public String confirm(EmailVerificationDTO dto) {

        // Username orqali profilni topamiz
        Optional<ProfileEntity> profileOptional =
                profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());

        if (profileOptional.isEmpty()) {
            throw new AppBadException("Profile not found");
        }

        ProfileEntity profile = profileOptional.get();

        // Profil allaqachon ACTIVE bo'lsa
        if (profile.getStatus() == ProfileStatus.ACTIVE) {
            throw new AppBadException("Profile already existed");
        }

        // Profile ID orqali ishlatilmagan verification kodni topamiz
        Optional<EmailVerificationEntity> verificationOptional =
                emailVerificationRepository.findByProfileIdAndUsedFalse(profile.getId());

        if (verificationOptional.isEmpty()) {
            throw new AppBadException("Verification code not found");
        }

        EmailVerificationEntity verification = verificationOptional.get();

        // Kodni tekshirish
        if (!verification.getCode().equals(dto.getCode())) {
            throw new AppBadException("Verification code is wrong");
        }

        // Kodning muddati tugaganligini tekshirish
        if (LocalDateTime.now().isAfter(verification.getExpiredDate())) {
            throw new AppBadException("Verification code expired");
        }

        // Kod ishlatilgan deb belgilash
        verification.setUsed(true);
        emailVerificationRepository.save(verification);

        // Profilni ACTIVE qilish
        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profile);

        return "Email successfully verified";
    }
}