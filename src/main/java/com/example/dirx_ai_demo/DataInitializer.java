package com.example.dirx_ai_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AuditLogRepository auditLogRepository;
    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(AuditLogRepository auditLogRepository, JdbcTemplate jdbcTemplate) {
        this.auditLogRepository = auditLogRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Wipe out data AND reset the auto-increment counter (ID sequence) back to 1
        System.out.println("--- Truncating table and restarting identity sequence... ---");
        jdbcTemplate.execute("TRUNCATE TABLE audit_logs RESTART IDENTITY CASCADE");

        // 2. Generate fresh mock audit logs starting cleanly from ID #1
        System.out.println("--- Database is empty, generating fresh mock audit logs... ---");

        List<AuditLog> mockLogs = List.of(
                new AuditLog("Peter", "DELETE", LocalDate.now().minusDays(2), "Brno", "User_Client_45"),
                new AuditLog("Peter", "CREATE", LocalDate.now().minusDays(1), "Prague", "Invoice_2026_001"),
                new AuditLog("Michael", "UPDATE", LocalDate.now().minusDays(3), "Brno", "Role_Admin"),
                new AuditLog("Michael", "DELETE", LocalDate.now().minusDays(1), "Ostrava", "Product_Laptop_X"),
                new AuditLog("Matt", "CREATE", LocalDate.now().minusDays(4), "Bratislava", "Database_Backup"),
                new AuditLog("David", "UPDATE", LocalDate.now().minusDays(1), "Brno", "Settings_Security"),
                new AuditLog("Peter", "DELETE", LocalDate.now(), "Brno", "User_Expired_Test"),
                new AuditLog("Matt", "DELETE", LocalDate.now().minusDays(4), "Bratislava", "Database_Backup")
        );

        auditLogRepository.saveAll(mockLogs);
        System.out.println("--- New mock data successfully saved! Total rows: " + auditLogRepository.count() + " ---");
    }
}