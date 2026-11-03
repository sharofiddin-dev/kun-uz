package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {

    @Query("select roles from ProfileRoleEntity where profileId =?1")
    List<ProfileRole> getRoleListByProfileId(Integer profileId);

    @Transactional
    @Modifying
    @Query("Delete from ProfileRoleEntity where profileId =?1 and roles =?2")
    void deleteByIdAndRoleEnum(Integer profileId, ProfileRole role);

    @Transactional
    @Modifying
    @Query("Delete from ProfileRoleEntity where profileId =?1")
    void deleteByProfileId(Integer profileId);
}
