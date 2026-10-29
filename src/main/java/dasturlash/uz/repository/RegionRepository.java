package dasturlash.uz.repository;

import dasturlash.uz.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {
    List<RegionEntity> findAllByOrderByOrderNumberAsc();
    List<RegionEntity> findByVisibleTrueOrderByOrderNumberAsc();
}
