package dasturlash.uz.entity;

import dasturlash.uz.enums.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "region")
@Getter
@Setter
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "name_uz", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language nameUz;

    @Column(name = "name_ru", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language nameRu;

    @Column(name = "name_en", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language nameEn;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;
}
