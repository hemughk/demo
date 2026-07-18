package com.example.demo.repository;

import com.example.demo.model.LoanRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRecordRepository extends JpaRepository<LoanRecord, Long> {}
