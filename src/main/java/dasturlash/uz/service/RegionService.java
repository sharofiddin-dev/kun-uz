package dasturlash.uz.service;

import dasturlash.uz.dto.request.ProfileRequestDTO;
import dasturlash.uz.dto.response.ProfileResponseDTO;
import dasturlash.uz.dto.response.RegionResponseDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
//    @Autowired
//    private RegionRepository repository;
//
//    // =====================================================
//    public RegionResponseDTO toDTO(RegionEntity entity) {
//        ProfileResponseDTO dto = new ProfileResponseDTO();
//        dto.setId(entity.getId());
//        dto.setName(entity.getName());
//        dto.setSurname(entity.getSurname());
//        dto.setEmail(entity.getEmail());
//        dto.setPhone(entity.getPhone());
//        dto.setStatus(entity.getStatus());
//        dto.setRole(entity.getRole());
//        dto.setVisible(entity.getVisible());
//        dto.setCreatedDate(entity.getCreatedDate());
//        return dto;
//    }
//
//    // =====================================================
//    public ProfileEntity toEntity(ProfileRequestDTO dto, ProfileEntity entity) {
//        entity.setName(dto.getName().trim());
//        entity.setSurname(dto.getSurname().trim());
//        entity.setEmail(dto.getEmail().trim());
//        entity.setPhone(dto.getPhone().trim());
//        entity.setPassword(dto.getPassword().trim());
//        return entity;
//    }
//
//    // =====================================================
//    public List<ProfileResponseDTO> toList(Iterable<ProfileEntity> iterable) {
//        List<ProfileResponseDTO> list = new LinkedList<>();
//        for (ProfileEntity entity : iterable) {
//            list.add(toDTO(entity));
//        }
//        return list;
//    }


}
