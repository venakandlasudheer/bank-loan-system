package com.loan.loan_service.repository;

import com.loan.loan_service.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByUsername(String username);

    List<LoanApplication> findByStatus(String status);

}