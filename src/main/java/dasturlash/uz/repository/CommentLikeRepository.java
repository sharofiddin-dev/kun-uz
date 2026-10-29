package dasturlash.uz.repository;

import dasturlash.uz.entity.CommentLikeEntity;
import dasturlash.uz.enums.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    Optional<CommentLikeEntity> findByProfileIdAndCommentId(Integer profileId, Long commentId);
    long countByCommentIdAndStatus(Long commentId, LikeStatus status);
}
