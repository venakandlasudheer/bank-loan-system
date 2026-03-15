package com.loan.loan_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private Integer tenure;  // in months

    @Column(nullable = false)
    private String status;   // PENDING, APPROVED, REJECTED

    @Column(nullable = false)
    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;

    private String adminRemarks;

}