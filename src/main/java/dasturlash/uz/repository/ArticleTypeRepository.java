package dasturlash.uz.repository;

import dasturlash.uz.entity.ArticleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity, Integer>{
    //FindByOrderName
    @Query("select a from ArticleTypeEntity a where a.orderNumber = :orderName")
    ArticleTypeEntity findByOrderName(@Param("orderName") String orderName);

    //FindByVisible
    @Query("select a from ArticleTypeEntity a where a.visible = :visible")
    List<ArticleTypeEntity> findByVisible(@Param("visible") boolean visible);

    //FindByCreatedDateBetween
    @Query("select a from ArticleTypeEntity a where a.createdDate between :from and :to")
    List<ArticleTypeEntity> findByCreatedDateBetween(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

}
