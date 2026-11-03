package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArticleSectionRepository extends JpaRepository<ArticleSectionEntity, Integer> {

    //Bu metod bitta Category shu Article'ga avval biriktirilganmi, tekshiradi.
    boolean existsByArticle_IdAndSection_Id(UUID articleId, Integer sectionId);
}