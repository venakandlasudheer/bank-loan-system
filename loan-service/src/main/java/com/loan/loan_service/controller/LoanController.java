package com.loan.loan_service.controller;

import com.loan.loan_service.dto.LoanRequest;
import com.loan.loan_service.dto.LoanStatusUpdate;
import com.loan.loan_service.model.LoanApplication;
import com.loan.loan_service.security.JwtUtil;
import com.loan.loan_service.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoanController {

    private final LoanService loanService;
    private final JwtUtil jwtUtil;

    private String extractUsername(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                return jwtUtil.extractUsername(token);
            }
        }
        return null;
    }

    private String extractRole(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                return jwtUtil.extractRole(token);
            }
        }
        return null;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Loan Service is running!");
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyLoan(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody LoanRequest request) {
        String username = extractUsername(authHeader);
        if (username == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        return ResponseEntity.ok(loanService.applyLoan(username, request));
    }

    @GetMapping("/my-loans")
    public ResponseEntity<?> getMyLoans(
            @RequestHeader("Authorization") String authHeader) {
        String username = extractUsername(authHeader);
        if (username == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        return ResponseEntity.ok(loanService.getMyLoans(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLoanById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id) {
        String username = extractUsername(authHeader);
        if (username == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllLoans(
            @RequestHeader("Authorization") String authHeader) {
        String role = extractRole(authHeader);
        if (role == null || !role.equals("ROLE_ADMIN"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin only!");
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/admin/status/{status}")
    public ResponseEntity<?> getLoansByStatus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String status) {
        String role = extractRole(authHeader);
        if (role == null || !role.equals("ROLE_ADMIN"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin only!");
        return ResponseEntity.ok(loanService.getLoansByStatus(status));
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateLoanStatus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @RequestBody LoanStatusUpdate update) {
        String role = extractRole(authHeader);
        if (role == null || !role.equals("ROLE_ADMIN"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin only!");
        return ResponseEntity.ok(loanService.updateLoanStatus(id, update));
    }

}