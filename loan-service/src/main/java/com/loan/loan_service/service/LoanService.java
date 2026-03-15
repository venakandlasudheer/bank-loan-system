package com.loan.loan_service.service;

import com.loan.loan_service.dto.LoanRequest;
import com.loan.loan_service.dto.LoanStatusUpdate;
import com.loan.loan_service.model.LoanApplication;
import com.loan.loan_service.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    // User applies for a loan
    public LoanApplication applyLoan(String username, LoanRequest request) {
        LoanApplication loan = new LoanApplication();
        loan.setUsername(username);
        loan.setAmount(request.getAmount());
        loan.setPurpose(request.getPurpose());
        loan.setTenure(request.getTenure());
        loan.setStatus("PENDING");
        loan.setAppliedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());
        return loanRepository.save(loan);
    }

    // User views their own loans
    public List<LoanApplication> getMyLoans(String username) {
        return loanRepository.findByUsername(username);
    }

    // Admin views all loans
    public List<LoanApplication> getAllLoans() {
        return loanRepository.findAll();
    }

    // Admin views loans by status
    public List<LoanApplication> getLoansByStatus(String status) {
        return loanRepository.findByStatus(status);
    }

    // Admin approves or rejects a loan
    public LoanApplication updateLoanStatus(Long loanId, LoanStatusUpdate update) {
        LoanApplication loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));

        loan.setStatus(update.getStatus());
        loan.setAdminRemarks(update.getAdminRemarks());
        loan.setUpdatedAt(LocalDateTime.now());

        return loanRepository.save(loan);
    }

    // Get single loan by id
    public LoanApplication getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));
    }

}