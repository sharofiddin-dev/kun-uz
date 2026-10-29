package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleLikeEntity;
import dasturlash.uz.enums.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArticleLikeRepository extends JpaRepository<ArticleLikeEntity, Long> {
    Optional<ArticleLikeEntity> findByProfileIdAndArticleId(Integer profileId, UUID articleId);
    long countByArticleIdAndStatus(UUID articleId, LikeStatus status);
}
