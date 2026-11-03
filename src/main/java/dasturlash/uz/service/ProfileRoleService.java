package dasturlash.uz.service;

import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileRoleService {
    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    public void create(Integer profileId, List<ProfileRole> roleList) {
        for (ProfileRole roleEnum : roleList) {
            ProfileRoleEntity entity = new ProfileRoleEntity();
            entity.setProfileId(profileId);
            entity.setRoles(roleEnum);
            profileRoleRepository.save(entity);
        }
    }

    public void merge(Integer profileId, List<ProfileRole> newList) {
        List<ProfileRole> oldList = profileRoleRepository.getRoleListByProfileId(profileId);

        //newList.stream().filter(n -> !oldList.contains(n)).forEach(pe -> create(profileId, pe)); // create
        for(ProfileRole nR: newList){ // create
            if(!oldList.contains(nR)){
                create(profileId, nR);
            }
        }

        //  oldList.stream().filter(old -> !newList.contains(old)).forEach(pe -> profileRoleRepository.deleteByIdAndRoleEnum(profileId, pe));

        for(ProfileRole oR: oldList){ // update
            if(!newList.contains(oR)){
                profileRoleRepository.deleteByIdAndRoleEnum(profileId, oR);
            }
        }
    }

    // old -> "ROLE_USER", "ROLE_ADMIN"
    // new -> "ROLE_USER", "ROLE_MODERATOR"
    public void create(Integer profileId, ProfileRole role) {
        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfileId(profileId);
        entity.setRoles(role);
        profileRoleRepository.save(entity);
    }

    public void deleteRolesByProfileId(Integer profileId) {
        profileRoleRepository.deleteByProfileId(profileId);
    }

    public List<ProfileRole> getByProfileId(Integer id) {
        return profileRoleRepository.getRoleListByProfileId(id);
    }
}
