package dasturlash.uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "region")
@Getter
@Setter
public class RegionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @Column(name = "name_uz", nullable = false)
    private String nameUz;

    @Column(name = "name_ru", nullable = false)
    private String nameRu;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "region_key")
    private String regionKey;

    @Column(name = "visible", nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column(name = "created_date", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    // one-to-many: bitta region-da bir nechta article bo'lishi mumkin
    // mappedBy -> ArticleEntity dagi "region" field'iga bog'langan
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<ArticleEntity> articles = new ArrayList<>();
}
