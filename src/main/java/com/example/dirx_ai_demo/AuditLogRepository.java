package com.example.dirx_ai_demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // JpaRepository nám automaticky vygeneruje všechny základní DB operace
}