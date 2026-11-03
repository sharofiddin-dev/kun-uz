package dasturlash.uz.repository;

import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.mapper.RegionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {

    Optional<RegionEntity> findByIdAndVisibleIsTrue(Integer id);

    @Transactional
    @Modifying
    @Query("update RegionEntity set visible = false where id = ?1")
    int updateVisibleById(Integer id);

    List<RegionEntity> findAllByVisibleIsTrueOrderByOrderNumberAsc();

    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN c.nameUz " +
            "   WHEN 'RU' THEN c.nameRu " +
            "   WHEN 'EN' THEN c.nameEn " +
            "END AS name, " +
            "c.orderNumber AS orderNumber, " +
            "c.regionKey AS regionKey " +
            "FROM RegionEntity c " +
            "WHERE c.visible = true ORDER BY c.orderNumber ASC")
    List<RegionMapper> getByLang(@Param("lang") String lang);

    Optional<RegionEntity> findByRegionKey(String regionKey);
}
