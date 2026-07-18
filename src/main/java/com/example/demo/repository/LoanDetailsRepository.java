package com.example.demo.repository;

import com.example.demo.model.LoanDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanDetailsRepository extends JpaRepository<LoanDetails, Long> {}
