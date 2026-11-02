package dasturlash.uz.repository;

import dasturlash.uz.dto.response.SectionFilterDTO;
import dasturlash.uz.entity.SectionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
public class SectionCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public Page<SectionEntity> filter(SectionFilterDTO filterDTO, int page, int size) {

        // content -> select
        StringBuilder selectQueryBuilder = new StringBuilder("Select s FROM SectionEntity as s  ");
        // total -> count
        StringBuilder countQueryBuilder = new StringBuilder("Select count(s) FROM SectionEntity as s  ");


        StringBuilder builder = new StringBuilder(" where s.visible = true");
        Map<String,Object> params = new HashMap<>();

        if(filterDTO.getOrderNumber() != null){
            builder.append(" and s.orderNumber = :orderNumber ");
            params.put("orderNumber", filterDTO.getOrderNumber());
        }

        if(filterDTO.getSectionKey() != null){
            builder.append(" and s.sectionKey = :sectionKey ");
            params.put("sectionKey", filterDTO.getSectionKey());
        }

        if(filterDTO.getNameEn() != null){
            builder.append(" and s.nameEn = :nameEn ");
            params.put("nameEn", filterDTO.getNameEn());
        }

        if(filterDTO.getNameRu() != null){
            builder.append(" and s.nameRu = :nameRu ");
            params.put("nameRu", filterDTO.getNameRu());
        }

        if(filterDTO.getNameUz() != null){
            builder.append(" and s.nameUz = :nameUz ");
            params.put("nameUz", filterDTO.getNameUz());
        }

        if(filterDTO.getCreatedDateFrom() != null) {
            builder.append(" and s.createdDate >= :dateFrom");
            params.put("dateFrom", filterDTO.getCreatedDateFrom());
        }

        if(filterDTO.getCreatedDateTo() != null) {
            builder.append(" and s.createdDate <= :dateTo ");
            params.put("dateTo", filterDTO.getCreatedDateTo());
        }



        //..
        selectQueryBuilder.append(builder);
        countQueryBuilder.append(builder);
        //
        Query selectQuery  = entityManager.createQuery(selectQueryBuilder.toString());
        Query countQuery  = entityManager.createQuery(countQueryBuilder.toString());

        selectQuery.setFirstResult(page * size); //offset (page-1)*size
        selectQuery.setMaxResults(size);
        //
        for(Map.Entry<String,Object> param : params.entrySet()){
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        // execute
        List<SectionEntity> profileEntityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();


        return new PageImpl<>(profileEntityList, PageRequest.of(page, size), totalElements );
    }
}
