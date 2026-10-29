package dasturlash.uz.repository;

import dasturlash.uz.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByArticleIdAndReplyIsNullAndVisibleTrueOrderByCreatedDateDesc(UUID articleId);
    List<CommentEntity> findByReplyIdAndVisibleTrueOrderByCreatedDateAsc(Long replyId);
    Page<CommentEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
