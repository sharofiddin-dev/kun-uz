package dasturlash.uz.mapper;

import dasturlash.uz.enums.ArticleStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ArticleMapper {

    UUID getId();

    String getTitle();

    String getDescription();

    String getContent();

    String getImageId();

    Integer getReadTime();

    Long getSharedCount();

    Long getViewCount();

    ArticleStatus getStatus();

    LocalDateTime getCreatedDate();

    LocalDateTime getPublishedDate();

    Boolean getVisible();

    Integer getRegionId();

    Integer getModeratorId();

    Integer getPublisherId();
}