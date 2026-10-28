package dasturlash.uz.service;

import dasturlash.uz.dto.request.ProfileRequestDTO;
import dasturlash.uz.dto.response.ProfileResponseDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repository;

    public ProfileResponseDTO create(ProfileRequestDTO dto) {
        ProfileEntity entity = toEntity(dto, new ProfileEntity());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        entity = repository.save(entity);
        return toDTO(entity);
    }

    public ProfileResponseDTO createAdmin(ProfileRequestDTO dto) {
        ProfileEntity entity = toEntity(dto, new ProfileEntity());

        entity.setRole(ProfileRole.ROLE_ADMIN);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());

        entity = repository.save(entity);

        return toDTO(entity);
    }


    // =====================================================
    public List<ProfileResponseDTO> getAll() {
        return toList(repository.findAll());
    }

    // =====================================================
    public ProfileResponseDTO getById(Integer id) {
        return toDTO(getEntity(id));
    }


    // =====================================================
    public ProfileEntity getEntity(Integer id) {
        Optional<ProfileEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Profile not found with id: " + id);
        }
        return optional.get();
    }


    // =====================================================
    public Boolean update(Integer id, ProfileRequestDTO dto) {
        ProfileEntity entity = getEntity(id);
        toEntity(dto, entity);
        repository.save(entity);
        return true;
    }


    // =====================================================
    public Boolean updateDetail(Integer id, ProfileRequestDTO dto) {
        ProfileEntity entity = getEntity(id);
        toEntity(dto, entity);
        repository.save(entity);
        return true;
    }


    // =====================================================
    public Boolean delete(Integer id) {
        repository.delete(getEntity(id));
        return true;
    }


    // =====================================================
    public Page<ProfileResponseDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        return repository.findAll(pageable).map(this::toDTO);
    }


    // =====================================================
    public Page<ProfileResponseDTO> paginationByRole(ProfileRole role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return repository.findByRole(role, pageable).map(this::toDTO);
    }


    // =====================================================
    public Page<ProfileResponseDTO> paginationByStatus(ProfileStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return repository.findByStatus(status, pageable).map(this::toDTO);
    }


    // =====================================================
    public List<ProfileResponseDTO> getByName(String name) {
        return toList(repository.findByNameIgnoreCase(name));
    }


    // =====================================================
    public ProfileResponseDTO getByEmail(String email) {
        Optional<ProfileEntity> optional = repository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Profile not found with email: " + email);
        }
        return toDTO(optional.get());
    }


    // =====================================================
    public List<ProfileResponseDTO> getByStatus(ProfileStatus status) {
        return toList(repository.findByStatus(status));
    }

    // =====================================================
    public List<ProfileResponseDTO> getByRole(ProfileRole role) {
        return toList(repository.findByRole(role));
    }

    // =====================================================
    public List<ProfileResponseDTO> getByVisible(Boolean visible) {
        return toList(repository.findByVisible(visible));
    }


    // =====================================================
    public List<ProfileResponseDTO> getBetweenDates(LocalDateTime from, LocalDateTime to) {
        return toList(repository.findByCreatedDateBetween(from, to));
    }

    // =====================================================
    public ProfileResponseDTO toDTO(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    // =====================================================
    public ProfileEntity toEntity(ProfileRequestDTO dto, ProfileEntity entity) {
        entity.setName(dto.getName().trim());
        entity.setSurname(dto.getSurname().trim());
        entity.setEmail(dto.getEmail().trim());
        entity.setPhone(dto.getPhone().trim());
        entity.setPassword(dto.getPassword().trim());
        return entity;
    }

    // =====================================================
    public List<ProfileResponseDTO> toList(Iterable<ProfileEntity> iterable) {
        List<ProfileResponseDTO> list = new LinkedList<>();
        for (ProfileEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }
}
