package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleEntity;
import dasturlash.uz.mapper.ArticleMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    @Query("""
            SELECT
                a.id AS id,
                a.title AS title,
                a.description AS description,
                a.content AS content,
                a.imageId AS imageId,
                a.readTime AS readTime,
                a.sharedCount AS sharedCount,
                a.viewCount AS viewCount,
                a.status AS status,
                a.createdDate AS createdDate,
                a.publishedDate AS publishedDate,
                a.visible AS visible,
                r.id AS regionId,
                m.id AS moderatorId,
                p.id AS publisherId
            FROM ArticleEntity a
            LEFT JOIN a.region r
            LEFT JOIN a.moderator m
            LEFT JOIN a.publisher p
            WHERE a.id = :id
            """)
    ArticleMapper findMapperById(@Param("id") UUID id);
}