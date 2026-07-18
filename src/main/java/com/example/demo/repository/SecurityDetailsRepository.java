package com.example.demo.repository;

import com.example.demo.model.SecurityDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityDetailsRepository extends JpaRepository<SecurityDetails, Long> {}
