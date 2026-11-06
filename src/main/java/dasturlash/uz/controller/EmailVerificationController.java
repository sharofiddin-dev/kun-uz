package dasturlash.uz.controller;

import dasturlash.uz.dto.auth.EmailVerificationDTO;
import dasturlash.uz.service.EmailVerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email-verification")
public class EmailVerificationController {
    @Autowired
    private EmailVerificationService emailVerificationService;

    @PostMapping("/confirm")
    public ResponseEntity<String> confirm(@Valid @RequestBody EmailVerificationDTO dto) {
        return ResponseEntity.ok(emailVerificationService.confirm(dto));
    }
}
