package dasturlash.uz.controller;

import dasturlash.uz.dto.request.SectionRequestDTO;
import dasturlash.uz.dto.response.SectionFilterDTO;
import dasturlash.uz.dto.response.SectionResponseDTO;
import dasturlash.uz.enums.Language;
import dasturlash.uz.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/section")
public class SectionController {

    @Autowired
    private SectionService service;

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<SectionResponseDTO> create(@Valid @RequestBody SectionRequestDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }

    // READ
    @GetMapping("/get")
    public ResponseEntity<List<SectionResponseDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<SectionResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody SectionRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@Valid @PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }

    // GET BY ORDER NUMBER
    @GetMapping("/get/by-order-number/{orderNumber}")
    public ResponseEntity<SectionResponseDTO> getByOrderNumber(@PathVariable Integer orderNumber){
        return ResponseEntity.ok(service.getByOrderNumber(orderNumber));
    }

    // GET BY ID
    @GetMapping("get/by-id/{id}")
    public ResponseEntity<SectionResponseDTO> getById(@PathVariable Integer id){
        return ResponseEntity.ok(service.getById(id));
    }

    // PAGINATION
    @GetMapping("/paging")
    public ResponseEntity<Page<SectionResponseDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "50") int size) {
        Page<SectionResponseDTO> result = service.allForPagination(page - 1, size);
        return ResponseEntity.ok(result);
    }

    // FILTER
    @PostMapping("/filter")
    public ResponseEntity<Page<SectionResponseDTO>> filter(@RequestBody SectionFilterDTO dto,
                                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.filter(dto, page-1, size));
    }

    // LANGUAGE
    @GetMapping("/lang")
    public ResponseEntity<List<SectionResponseDTO>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") Language language) {
        return ResponseEntity.ok(service.getAllByLang(language));
    }
}
