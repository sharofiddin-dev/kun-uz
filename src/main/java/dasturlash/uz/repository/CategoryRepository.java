package dasturlash.uz.repository;

import dasturlash.uz.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CategoryRepository  extends JpaRepository<CategoryEntity, Integer> {
    //FindByCategoryId
    @Query("select c from CategoryEntity c where c.categoryId = :categoryId")
    List<CategoryEntity> findByCategoryId(@Param("categoryId") Integer categoryId);

    //FindByVisible
    @Query("select c from CategoryEntity c where c.visible = :visible")
    List<CategoryEntity> findByVisible(@Param("visible") Boolean visible);

    //FindByCreatedDateBetween
    @Query("select c from CategoryEntity c where c.createdDate between :from and :to")
    List<CategoryEntity> findByCreatedDateBetween(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}
