package com.example.skilllog.api.error;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(basePackages = "com.example.skilllog.api")
// ↑ API配下のControllerだけを対象にする（Web側に影響しない）
public class ApiExceptionHandler {

    // 入力バリデーションエラー（@Valid）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(FieldError::getField,
                                      DefaultMessageSourceResolvable::getDefaultMessage,
                                      (a,b)->a));
        return ResponseEntity.badRequest().body(Map.of(
            "message", "validation error",
            "errors", errors
        ));
    }

    // 見つからない系（任意の自作例外）
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of("message", ex.getMessage()));
    }
}
