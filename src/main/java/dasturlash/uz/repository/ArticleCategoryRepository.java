package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategoryEntity, Integer> {

    //Bu metod bitta Category shu Article'ga avval biriktirilganmi, tekshiradi.
    boolean existsByArticle_IdAndCategory_CategoryId(
            UUID articleId,
            Integer categoryId
    );
}