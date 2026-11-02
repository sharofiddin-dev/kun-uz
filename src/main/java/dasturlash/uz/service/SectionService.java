package dasturlash.uz.service;

import dasturlash.uz.dto.request.SectionRequestDTO;
import dasturlash.uz.dto.response.SectionFilterDTO;
import dasturlash.uz.dto.response.SectionResponseDTO;
import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.enums.Language;
import dasturlash.uz.exception.AppBadException;
import dasturlash.uz.mapper.SectionMapper;
import dasturlash.uz.repository.SectionCustomRepository;
import dasturlash.uz.repository.SectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionService {
    @Autowired
    private SectionRepository repository;

    @Autowired
    private SectionCustomRepository sectionCustomRepository;

    public SectionResponseDTO create(SectionRequestDTO dto) {
        Optional<SectionEntity> optional = repository.findBySectionKey(dto.getSectionKey());
        if (optional.isPresent()) {
            throw new AppBadException("Section key already exists");
        }

        SectionEntity entity = new SectionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setSectionKey(dto.getSectionKey());
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);

        return toDTO(entity);
    }

    private SectionResponseDTO toDTO(SectionEntity entity) {
        SectionResponseDTO dto = new SectionResponseDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setSectionKey(entity.getSectionKey());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public SectionResponseDTO update(Integer id, SectionRequestDTO dto) {
        SectionEntity entity = repository.findByIdAndVisibleIsTrue(id)
                .orElseThrow(() -> new AppBadException("Section not found"));
        Optional<SectionEntity> keyOptional = repository.findBySectionKey(dto.getSectionKey()); // Jahon
        if (keyOptional.isPresent() && !id.equals(keyOptional.get().getId())) {
            throw new AppBadException("Section present");
        }
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setSectionKey(dto.getSectionKey());
        repository.save(entity);
        return toDTO(entity);
    }

    public Boolean delete(Integer id) {
        int result = repository.updateVisibleById(id);

        if (result == 0) {
            throw new AppBadException("Section not found");
        }

        return true;
    }

    public List<SectionResponseDTO> getAll() {
        List<SectionEntity> entityList = repository.findAllByVisibleTrue();

        List<SectionResponseDTO> dtos = new LinkedList<>();

        entityList.forEach(entity -> dtos.add(toDTO(entity)));

        return dtos;
    }

    public SectionResponseDTO getByOrderNumber(Integer orderNumber) {
        SectionEntity entity = (SectionEntity) repository
                .findByOrderNumberAndVisibleTrue(orderNumber)
                .orElseThrow(() -> new AppBadException("Section not found"));

        return toDTO(entity);
    }

    public List<SectionResponseDTO> getAllByLang(Language language) {
        Iterable<SectionMapper> iterable = repository.getByLang(language.name());

        List<SectionResponseDTO> dtoList = new LinkedList<>();

        iterable.forEach(mapper -> {
            SectionResponseDTO dto = new SectionResponseDTO();
            dto.setId(mapper.getId());
            dto.setSectionKey(mapper.getSectionKey());
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



//    public List<SectionResponseDTO> getAllByLang(Language language) {
//        Iterable<SectionMapper> iterable = repository.getByLang(language.name());
//        List<SectionResponseDTO> dtoList = new LinkedList<>();
//        iterable.forEach(mapper -> {
//            SectionResponseDTO dto = new SectionResponseDTO();
//            dto.setId(mapper.getId());
//            dto.setSectionKey(mapper.getSectionKey());
//            dtoList.add(dto);
//        });
//        return dtoList;
//    }
//
//    private SectionResponseDTO toLangResponseDto(SectionEntity entity, Language lang) {
//        SectionResponseDTO dto = new SectionResponseDTO();
//        dto.setId(entity.getId());
//        dto.setSectionKey(entity.getSectionKey());
//        switch (lang) {
//            case UZ:
//                dto.setNameUz(entity.getNameUz());
//                break;
//            case RU:
//                dto.setNameRu(entity.getNameRu());
//                break;
//            case EN:
//                dto.setNameEn(entity.getNameEn());
//                break;
//        }
//        return dto;
//    }

    public Page<SectionResponseDTO> allForPagination(int page, int size) {

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "orderNumber")
        );

        Page<SectionEntity> pageResult = repository.findAll(pageRequest);

        Long totalPages = pageResult.getTotalElements();

        List<SectionResponseDTO> dtoList = new LinkedList<>();

        for (SectionEntity entity : pageResult.getContent()) {
            dtoList.add(toDTO(entity));
        }

        return new PageImpl<>(dtoList, pageRequest, totalPages);
    }


    public Page<SectionResponseDTO> filter(SectionFilterDTO filter, int page, int size) {

        Page<SectionEntity> result = sectionCustomRepository.filter(filter, page, size);

        long totalCount = result.getTotalElements();

        List<SectionResponseDTO> dtoList = new LinkedList<>();

        for (SectionEntity entity : result.getContent()) {

            SectionResponseDTO dto = new SectionResponseDTO();

            dto.setId(entity.getId());
            dto.setSectionKey(entity.getSectionKey());
            dto.setOrderNumber(entity.getOrderNumber());
            dto.setNameUz(entity.getNameUz());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setCreatedDate(entity.getCreatedDate());

            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, PageRequest.of(page, size), totalCount);
    }

    public SectionResponseDTO getById(Integer id) {
        Optional<SectionEntity> entityOptional = repository.findById(id);
        return toDTO(entityOptional.orElseThrow(() -> new AppBadException("Section not found")));
    }
}
