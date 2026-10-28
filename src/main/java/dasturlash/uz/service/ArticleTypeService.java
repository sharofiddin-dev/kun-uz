package dasturlash.uz.service;

import dasturlash.uz.dto.request.ArticleTypeRequestDTO;
import dasturlash.uz.dto.response.ArticleTypeResponseDTO;
import dasturlash.uz.entity.ArticleTypeEntity;
import dasturlash.uz.enums.Language;
import dasturlash.uz.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository repository;

    public ArticleTypeResponseDTO create(ArticleTypeRequestDTO dto) {
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(Language.UZ);
        entity.setNameRu(Language.RU);
        entity.setNameEn(Language.EN);
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        entity = repository.save(entity);
        return toDTO(entity);
    }

    private ArticleTypeResponseDTO toDTO(ArticleTypeEntity entity) {
        ArticleTypeResponseDTO dto = new ArticleTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }




}
