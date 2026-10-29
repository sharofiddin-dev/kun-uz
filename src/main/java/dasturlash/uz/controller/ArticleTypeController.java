package dasturlash.uz.controller;

import dasturlash.uz.dto.request.ArticleTypeRequestDTO;
import dasturlash.uz.dto.response.ArticleTypeResponseDTO;
import dasturlash.uz.dto.response.ArticleTypeResponseShortDTO;
import dasturlash.uz.service.ArticleTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article-type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService service;

    @PostMapping("/create")
    public ResponseEntity<ArticleTypeResponseDTO> create(@Valid @RequestBody ArticleTypeRequestDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }
    @PutMapping("/update-by/id/{id}")
    public ResponseEntity<ArticleTypeResponseDTO> update(@Valid @RequestBody ArticleTypeRequestDTO dto, @PathVariable("id") Integer id){
       return ResponseEntity.ok(service.update(dto,id));
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<ArticleTypeResponseShortDTO> update(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.getById(id));
    }
    @DeleteMapping("/delete-by/id/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.deleteById(id));
    }
}
