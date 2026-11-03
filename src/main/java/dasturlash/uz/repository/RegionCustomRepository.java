package dasturlash.uz.repository;

import dasturlash.uz.dto.response.RegionFilterDTO;
import dasturlash.uz.entity.RegionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RegionCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public Page<RegionEntity> filter(RegionFilterDTO filterDTO, int page, int size) {

        // content -> select
        StringBuilder selectQueryBuilder = new StringBuilder("Select r FROM RegionEntity as r  ");
        // total -> count
        StringBuilder countQueryBuilder = new StringBuilder("Select count(r) FROM RegionEntity as r  ");

        StringBuilder builder = new StringBuilder(" where r.visible = true");
        Map<String, Object> params = new HashMap<>();

        if (filterDTO.getOrderNumber() != null) {
            builder.append(" and r.orderNumber = :orderNumber ");
            params.put("orderNumber", filterDTO.getOrderNumber());
        }

        if (filterDTO.getRegionKey() != null) {
            builder.append(" and r.regionKey = :regionKey ");
            params.put("regionKey", filterDTO.getRegionKey());
        }

        if (filterDTO.getNameUz() != null) {
            builder.append(" and lower(r.nameUz) like :nameUz ");
            params.put("nameUz", "%" + filterDTO.getNameUz().toLowerCase() + "%");
        }

        if (filterDTO.getNameRu() != null) {
            builder.append(" and lower(r.nameRu) like :nameRu ");
            params.put("nameRu", "%" + filterDTO.getNameRu().toLowerCase() + "%");
        }

        if (filterDTO.getNameEn() != null) {
            builder.append(" and lower(r.nameEn) like :nameEn ");
            params.put("nameEn", "%" + filterDTO.getNameEn().toLowerCase() + "%");
        }

        if (filterDTO.getCreatedDateFrom() != null) {
            builder.append(" and r.createdDate >= :dateFrom ");
            params.put("dateFrom", filterDTO.getCreatedDateFrom());
        }

        if (filterDTO.getCreatedDateTo() != null) {
            builder.append(" and r.createdDate <= :dateTo ");
            params.put("dateTo", filterDTO.getCreatedDateTo());
        }

        selectQueryBuilder.append(builder).append(" order by r.orderNumber asc ");
        countQueryBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectQueryBuilder.toString());
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());

        selectQuery.setFirstResult(page * size); // offset (page-1)*size
        selectQuery.setMaxResults(size);

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<RegionEntity> regionEntityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PageImpl<>(regionEntityList, PageRequest.of(page, size), totalElements);
    }
}
