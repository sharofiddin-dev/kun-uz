package dasturlash.uz.service;

import dasturlash.uz.dto.request.ArticleTypeRequestDTO;
import dasturlash.uz.dto.response.ArticleTypeResponseDTO;
import dasturlash.uz.dto.response.ArticleTypeResponseShortDTO;
import dasturlash.uz.entity.ArticleTypeEntity;
import dasturlash.uz.repository.ArticleTypeRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
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
        entity.setNameUz(dto.getNameUz().trim());
        entity.setNameRu(dto.getNameRu().trim());
        entity.setNameEn(dto.getNameEn().trim());
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


    public ArticleTypeResponseDTO update(@Valid ArticleTypeRequestDTO dto, Integer id) {
      ArticleTypeEntity entity = repository.findById(id).get();

      entity.setNameUz(dto.getNameUz());
      entity.setNameRu(dto.getNameRu());
      entity.setNameEn(dto.getNameEn());
      entity.setOrderNumber(dto.getOrderNumber());
      repository.save(entity);
      return toDTO(entity);
    }

    public ArticleTypeResponseShortDTO getById(@Valid Integer id) {
      ArticleTypeEntity entity = repository.findById(id).get();
      return toShortDTO(entity);
    }


    private ArticleTypeResponseShortDTO  toShortDTO(ArticleTypeEntity entity) {
        ArticleTypeResponseShortDTO dto = new ArticleTypeResponseShortDTO();
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }

    public Boolean deleteById(Integer id) {

       if(repository.findById(id).isPresent()) {
           repository.deleteById(id);
           return true;
       }
       return false;

    }
}
