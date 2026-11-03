package dasturlash.uz.controller;

import dasturlash.uz.dto.request.RegionRequestDTO;
import dasturlash.uz.dto.response.RegionFilterDTO;
import dasturlash.uz.dto.response.RegionResponseDTO;
import dasturlash.uz.enums.Language;
import dasturlash.uz.service.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    @Autowired
    private RegionService service;

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<RegionResponseDTO> create(@Valid @RequestBody RegionRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // READ
    @GetMapping("/get")
    public ResponseEntity<List<RegionResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<RegionResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody RegionRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    // GET BY ID
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity<RegionResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // PAGINATION
    @GetMapping("/paging")
    public ResponseEntity<Page<RegionResponseDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                              @RequestParam(value = "size", defaultValue = "50") int size) {
        return ResponseEntity.ok(service.allForPagination(page - 1, size));
    }

    // FILTER
    @PostMapping("/filter")
    public ResponseEntity<Page<RegionResponseDTO>> filter(@RequestBody RegionFilterDTO dto,
                                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.filter(dto, page - 1, size));
    }

    // LANGUAGE
    @GetMapping("/lang")
    public ResponseEntity<List<RegionResponseDTO>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "uz") Language language) {
        return ResponseEntity.ok(service.getAllByLang(language));
    }
}
