package dasturlash.uz.controller;

import dasturlash.uz.dto.request.ProfileRequestDTO;
import dasturlash.uz.dto.response.ProfileResponseDTO;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService service;
    @PostMapping
    public ResponseEntity<ProfileResponseDTO> create(@Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // =====================================================
    // GET ALL — GET /api/profile
    // =====================================================
    @GetMapping("/get-all")
    public ResponseEntity<List<ProfileResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // =====================================================
    // GET BY ID — GET /api/profile/{id}
    // =====================================================
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // =====================================================
    // TZ: "Update Profile (ADMIN)"
    // role, status, visible ham yangilanadi
    // =====================================================
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateById(@PathVariable Integer id,
                                              @Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // =====================================================
    // TZ: "Profile updates own details"
    // Faqat shaxsiy maydonlar; role/status/visible o'zgarmaydi
    // =====================================================
    @PutMapping("/{id}/detail")
    public ResponseEntity<Boolean> updateDetail(@PathVariable Integer id,
                                                @Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.ok(service.updateDetail(id, dto));
    }

    // =====================================================
    // TZ: "Delete Profile By Id (ADMIN)" -> 204 NO_CONTENT (body yo'q)
    // =====================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =====================================================
    // TZ: "Profile List (ADMIN) (Pagination)"
    // page 0-indeksli, createdDate bo'yicha kamayish tartibida
    // =====================================================
    @GetMapping("/page")
    public ResponseEntity<Page<ProfileResponseDTO>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.pagination(page, size));
    }

    // =====================================================
    // GET by Role
    // =====================================================
    @GetMapping("/page/by-role")
    public ResponseEntity<Page<ProfileResponseDTO>> paginationByRole(
            @RequestParam ProfileRole role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.paginationByRole(role, page, size));
    }

    // =====================================================
    // 4-VAZIFA: PAGINATION BY STATUS — GET /api/profile/page/by-status?status=ACTIVE
    // =====================================================
    @GetMapping("/page/by-status")
    public ResponseEntity<Page<ProfileResponseDTO>> paginationByStatus(
            @RequestParam ProfileStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.paginationByStatus(status, page, size));
    }

    // =====================================================
    // QIDIRUV: NAME — GET /api/profile/search/by-name?name=Ali
    // =====================================================
    @GetMapping("/search/by-name")
    public ResponseEntity<List<ProfileResponseDTO>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(service.getByName(name));
    }

    // =====================================================
    // QIDIRUV: EMAIL — GET /api/profile/search/by-email?email=ali@mail.com
    // =====================================================
    @GetMapping("/search/by-email")
    public ResponseEntity<ProfileResponseDTO> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(service.getByEmail(email));
    }

    // =====================================================
    // QIDIRUV: STATUS — GET /api/profile/search/by-status?status=ACTIVE
    // =====================================================
    @GetMapping("/search/by-status")
    public ResponseEntity<List<ProfileResponseDTO>> getByStatus(@RequestParam ProfileStatus status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    // =====================================================
    // QIDIRUV: ROLE — GET /api/profile/search/by-role?role=ROLE_ADMIN
    // =====================================================
    @GetMapping("/search/by-role")
    public ResponseEntity<List<ProfileResponseDTO>> getByRole(@RequestParam ProfileRole role) {
        return ResponseEntity.ok(service.getByRole(role));
    }

    // =====================================================
    // QIDIRUV: VISIBLE — GET /api/profile/search/by-visible?visible=true
    // =====================================================
    @GetMapping("/search/by-visible")
    public ResponseEntity<List<ProfileResponseDTO>> getByVisible(@RequestParam Boolean visible) {
        return ResponseEntity.ok(service.getByVisible(visible));
    }

    // =====================================================
    // QIDIRUV: SANALAR ORALIG'I — GET /api/profile/search/between-dates
    // Sana LocalDateTime sifatida, @DateTimeFormat(ISO.DATE_TIME) bilan parse
    // =====================================================
    @GetMapping("/search/between-dates")
    public ResponseEntity<List<ProfileResponseDTO>> getBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(service.getBetweenDates(from, to));
    }
}
