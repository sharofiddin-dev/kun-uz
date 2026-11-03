package dasturlash.uz.service;

import dasturlash.uz.dto.request.RegionRequestDTO;
import dasturlash.uz.dto.response.RegionFilterDTO;
import dasturlash.uz.dto.response.RegionResponseDTO;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.enums.Language;
import dasturlash.uz.exception.AppBadException;
import dasturlash.uz.mapper.RegionMapper;
import dasturlash.uz.repository.RegionCustomRepository;
import dasturlash.uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository repository;

    @Autowired
    private RegionCustomRepository regionCustomRepository;

    public RegionResponseDTO create(RegionRequestDTO dto) {
        Optional<RegionEntity> optional = repository.findByRegionKey(dto.getRegionKey());
        if (optional.isPresent()) {
            throw new AppBadException("Region key already exists");
        }

        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setRegionKey(dto.getRegionKey());
        entity.setVisible(Boolean.TRUE);
        repository.save(entity);

        return toDTO(entity);
    }

    public RegionResponseDTO update(Integer id, RegionRequestDTO dto) {
        RegionEntity entity = repository.findByIdAndVisibleIsTrue(id)
                .orElseThrow(() -> new AppBadException("Region not found"));

        Optional<RegionEntity> keyOptional = repository.findByRegionKey(dto.getRegionKey());
        if (keyOptional.isPresent() && !id.equals(keyOptional.get().getId())) {
            throw new AppBadException("Region present");
        }

        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setRegionKey(dto.getRegionKey());
        repository.save(entity);

        return toDTO(entity);
    }

    public Boolean delete(Integer id) {
        int result = repository.updateVisibleById(id);

        if (result == 0) {
            throw new AppBadException("Region not found");
        }

        return true;
    }

    public RegionResponseDTO getById(Integer id) {
        RegionEntity entity = repository.findByIdAndVisibleIsTrue(id)
                .orElseThrow(() -> new AppBadException("Region not found"));
        return toDTO(entity);
    }

    public List<RegionResponseDTO> getAll() {
        List<RegionEntity> entityList = repository.findAllByVisibleIsTrueOrderByOrderNumberAsc();

        List<RegionResponseDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));

        return dtoList;
    }

    public List<RegionResponseDTO> getAllByLang(Language language) {
        List<RegionMapper> mapperList = repository.getByLang(language.name());

        List<RegionResponseDTO> dtoList = new LinkedList<>();

        mapperList.forEach(mapper -> {
            RegionResponseDTO dto = new RegionResponseDTO();
            dto.setId(mapper.getId());
            dto.setRegionKey(mapper.getRegionKey());
            dto.setOrderNumber(mapper.getOrderNumber());

            switch (language) {
                case UZ -> dto.setNameUz(mapper.getName());
                case RU -> dto.setNameRu(mapper.getName());
                case EN -> dto.setNameEn(mapper.getName());
            }

            dtoList.add(dto);
        });

        return dtoList;
    }

    public Page<RegionResponseDTO> allForPagination(int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "orderNumber")
        );

        Page<RegionEntity> pageResult = repository.findAll(pageRequest);

        List<RegionResponseDTO> dtoList = new LinkedList<>();
        pageResult.getContent().forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<>(dtoList, pageRequest, pageResult.getTotalElements());
    }

    public Page<RegionResponseDTO> filter(RegionFilterDTO filter, int page, int size) {
        Page<RegionEntity> result = regionCustomRepository.filter(filter, page, size);

        List<RegionResponseDTO> dtoList = new LinkedList<>();
        result.getContent().forEach(entity -> dtoList.add(toDTO(entity)));

        return new PageImpl<>(dtoList, PageRequest.of(page, size), result.getTotalElements());
    }

    private RegionResponseDTO toDTO(RegionEntity entity) {
        RegionResponseDTO dto = new RegionResponseDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setRegionKey(entity.getRegionKey());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
