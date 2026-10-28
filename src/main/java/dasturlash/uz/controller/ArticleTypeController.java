package dasturlash.uz.controller;

import dasturlash.uz.dto.request.ArticleTypeRequestDTO;
import dasturlash.uz.dto.response.ArticleTypeResponseDTO;
import dasturlash.uz.service.ArticleTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article/type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService service;

    @PostMapping("/create")
    public ResponseEntity<ArticleTypeResponseDTO> create(@Valid @RequestBody ArticleTypeRequestDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }
}
