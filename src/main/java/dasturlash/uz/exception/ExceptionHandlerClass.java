/*
 * =============================================================================
 *  GLOBAL EXCEPTION HANDLER — TZ "Texnik talablar" (11-band)
 * =============================================================================
 *  Vazifa : Butun ilovadagi exception'larni markazlashtirilgan usulda ushlab,
 *           mijozga to'g'ri HTTP status va tushunarli xabar qaytarish.
 *           @RestControllerAdvice orqali Spring bu klassni avtomatik
 *           topadi — Controller ichida try/catch yozish shart emas.
 *
 *  Qanday qilingan:
 *   - ResponseEntityExceptionHandler dan meros olingan — Spring'ning
 *     standart HTTP exception'larini (404, 405, 415 va h.k.) avtomatik
 *     tarzda to'g'ri status bilan qaytaradi.
 *   - Qo'shimcha handler'lar quyidagilarni ushlaydi:
 *
 *  Exception / Spring default         | Status | Qaytariladigan xabar
 *  -----------------------------------|--------|-----------------------------------
 *  MethodArgumentNotValidException     |  400   | "timestamp", "status", "errors" (har bir
 *      (@Valid / @NotBlank / @Size      |        |  maydon xatosi alohida ro'yxatda)
 *       xatolari)                      |        |
 *  IllegalArgumentException           |  400   | xabar matni (Service da "topilmadi" holatlari)
 *  TypeMismatchException               |  400   | "Invalid value format was given for parameter X"
 *      (query param turi mos kelmadi)  |        |
 *  HttpMessageNotReadableException     |  400   | enum bo'lsa — ruxsat etilgan qiymatlar ro'yxati
 *      (JSON noto'g'ri / enum xato)    |        | (masalan: "Invalid value (X) values allowed: [MALE, FEMALE]")
 *  DataIntegrityViolationException    |  409   | "database integrity rule buzildi" (FK/unique)
 *  ConstraintViolationException        |  400   | "timestamp", "status", "errors"
 *      (@Validated parametr bo'yicha)  |        |
 *
 *  Izohlar:
 *   - Not found (404) alohida exception turi bilan emas, IllegalArgumentException
 *     orqali 400 qaytariladi (Service qatlamida "X not found with id: ...").
 *   - Ma'lumotlar Map (LinkedHashMap) ko'rinishida qaytariladi — tartibi
 *     saqlanishi uchun.
 *   - Barcha javoblarda "timestamp" va "status" maydonlari mavjud.
 * =============================================================================
 */
package dasturlash.uz.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatusCode status,
                                                               WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = new LinkedList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }

        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        String paramName = "parameter";
        if (ex instanceof MethodArgumentTypeMismatchException) {
            paramName = ((MethodArgumentTypeMismatchException) ex).getName();
        }

        body.put("error", String.format("Invalid value format was given for parameter %s", paramName));
        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException) {
            if (((InvalidFormatException) cause).getTargetType().isEnum()) {
                InvalidFormatException exception = (InvalidFormatException) cause;

                StringBuilder error = new StringBuilder(200);
                error.append("Invalid value (")
                        .append(exception.getValue())
                        .append(") values allowed:")
                        .append(" [");

                Object[] allowedConstants = exception.getTargetType().getEnumConstants();
                for (int i = 0; i < allowedConstants.length; i++) {
                    error.append(allowedConstants[i]);
                    if (i < allowedConstants.length - 1) {
                        error.append(", ");
                    }
                }

                error.append("]");
                body.put("error", error.toString());
                return new ResponseEntity<>(body, headers, status);
            }
        }

        body.put("error", "Malformed or unreadable json body");
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "The request violates a database integrity rule (e.g., duplicate entry or invalid relationship).");
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        List<String> errors = new LinkedList<>();
        ex.getConstraintViolations().forEach(violation -> errors.add(violation.getMessage()));

        body.put("errors", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
