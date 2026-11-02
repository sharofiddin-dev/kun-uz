package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.mapper.SectionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Integer> {
    Optional<SectionEntity> findByIdAndVisibleIsTrue(Integer id);

    @Transactional
    @Modifying
    @Query("update SectionEntity set visible = false where id = ?1")
    int updateVisibleById(Integer id);

    @Query("From SectionEntity where visible = true order by orderNumber")
    List<SectionEntity> findAll();

    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN c.nameUz " +
            "   WHEN 'RU' THEN c.nameRu " +
            "   WHEN 'EN' THEN c.nameEn " +
            "END AS name, " +
            "c.orderNumber AS orderNumber, " +
            "c.sectionKey AS sectionKey " +
            "FROM SectionEntity c " +
            "WHERE c.visible = true ORDER BY c.orderNumber ASC")
    List<SectionMapper> getByLang(@Param("lang") String lang);

    Optional<SectionEntity> findBySectionKey(String key);

    @Query("select c from SectionEntity c where c.id = :id")
    List<SectionEntity> findById(@Param("id") String name);

    @Query("select c from SectionEntity c where c.orderNumber = :num")
    Optional<SectionEntity> findByOrderNumber(@Param("num") int num);

    @Query("select c from SectionEntity c where c.id = :id")
    Optional<Boolean> findByIdForDelete(@Param("id") Integer id);

    List<SectionEntity> findAllByVisibleTrue();

    Optional<Object> findByOrderNumberAndVisibleTrue(Integer orderNumber);
}
