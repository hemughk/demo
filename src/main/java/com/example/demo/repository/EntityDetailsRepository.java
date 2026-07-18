package com.example.demo.repository;

import com.example.demo.model.EntityDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityDetailsRepository extends JpaRepository<EntityDetails, Long> {}
