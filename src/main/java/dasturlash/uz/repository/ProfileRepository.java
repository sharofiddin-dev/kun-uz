package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    // List<ProfileEntity> findByNameIgnoreCase(String name);
    @Query("select p from ProfileEntity p where lower(p.name) = lower(:name)")
    List<ProfileEntity> findByNameIgnoreCase(@Param("name") String name);

    //FindByEmail
    @Query("select p from ProfileEntity p where p.email = :email")
    Optional<ProfileEntity> findByEmail(@Param("email") String email);

    //FindByStatus
    @Query("select p from ProfileEntity p where p.status = :status")
    List<ProfileEntity> findByStatus(@Param("status") ProfileStatus status);

    //FindByRole
    @Query("select p from ProfileEntity p where p.role = :role")
    List<ProfileEntity> findByRole(@Param("role") ProfileRole role);

    //FindByVisible
    @Query("select p from ProfileEntity p where p.visible = :visible")
    List<ProfileEntity> findByVisible(@Param("visible") Boolean visible);

    //FindByCreatedDateBetween
    @Query("select p from ProfileEntity p where p.createdDate between :from and :to")
    List<ProfileEntity> findByCreatedDateBetween(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

    //Paging by Role
    @Query("select p from ProfileEntity p where p.role = :role")
    Page<ProfileEntity> findByRole(@Param("role") ProfileRole role, Pageable pageable);

    //Paging by Status
    @Query("select p from ProfileEntity p where p.status = :status")
    Page<ProfileEntity> findByStatus(@Param("status") ProfileStatus status, Pageable pageable);
}