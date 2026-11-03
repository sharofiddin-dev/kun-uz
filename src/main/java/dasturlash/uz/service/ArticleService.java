package dasturlash.uz.service;
import dasturlash.uz.dto.request.ArticleRequestDTO;
import dasturlash.uz.dto.response.ArticleResponseDTO;
import dasturlash.uz.entity.*;
import dasturlash.uz.enums.ArticleStatus;
import dasturlash.uz.exception.AppBadException;
import dasturlash.uz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;
    @Autowired
    private ArticleSectionRepository articleSectionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ProfileRepository profileRepository;

    // CREATE
    public ArticleResponseDTO create(ArticleRequestDTO dto, Integer moderatorId) {

        ArticleEntity entity = new ArticleEntity();

        // Request DTO dan Entity ga o'tkazish
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setReadTime(dto.getReadTime());

        // Default qiymatlar
        entity.setSharedCount(0L);
        entity.setViewCount(0L);
        entity.setVisible(true);
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        entity.setCreatedDate(LocalDateTime.now());

        // Region biriktirish
        if (dto.getRegionId() != null) {
            RegionEntity region = regionRepository.findById(dto.getRegionId())
                    .orElseThrow(() -> new AppBadException("Region not found"));

            entity.setRegion(region);
        }

        // Moderator biriktirish
        ProfileEntity moderator = profileRepository.findById(moderatorId)
                .orElseThrow(() -> new AppBadException("Moderator not found"));

        entity.setModerator(moderator);

        // Database ga saqlash
        articleRepository.save(entity);

        return convertToDTO(entity);
    }

    private ArticleResponseDTO convertToDTO(ArticleEntity entity) {

        ArticleResponseDTO dto = new ArticleResponseDTO();

        // Asosiy ma'lumotlar
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setReadTime(entity.getReadTime());

        // Count
        dto.setSharedCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());

        // Status va sanalar
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setVisible(entity.getVisible());

        // Region ID
        if (entity.getRegion() != null) {
            dto.setRegionId(entity.getRegion().getId());
        }

        // Moderator ID
        if (entity.getModerator() != null) {
            dto.setModeratorId(entity.getModerator().getId());
        }

        // Publisher ID
        if (entity.getPublisher() != null) {
            dto.setPublisherId(entity.getPublisher().getId());
        }

        // Category ID lar
        List<Integer> categoryList = new ArrayList<>();

        for (ArticleCategoryEntity articleCategory : entity.getArticleCategories()) {
            categoryList.add(articleCategory.getCategory().getCategoryId());
        }

        dto.setCategoryList(categoryList);

        // Section ID lar
        List<Integer> sectionList = new ArrayList<>();

        for (ArticleSectionEntity articleSection : entity.getArticleSections()) {
            sectionList.add(articleSection.getSection().getId());
        }

        dto.setSectionList(sectionList);

        return dto;
    }

}