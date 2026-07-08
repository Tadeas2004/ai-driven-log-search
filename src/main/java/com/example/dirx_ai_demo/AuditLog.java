package com.example.dirx_ai_demo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String eventType;
    private LocalDate eventDate;
    private String location;
    private String targetResource;

    public AuditLog() {}

    public AuditLog(String username, String eventType, LocalDate eventDate, String location, String targetResource) {
        this.username = username;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.location = location;
        this.targetResource = targetResource;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEventType() { return eventType; }
    public LocalDate getEventDate() { return eventDate; }
    public String getLocation() { return location; }
    public String getTargetResource() { return targetResource; }

    @Override
    public String toString() {
        return "AuditLog{" + "id=" + id + ", username='" + username + '\'' + ", eventType='" + eventType + '\'' +
                ", eventDate=" + eventDate + ", location='" + location + '\'' + ", targetResource='" + targetResource + '\'' + '}';
    }
}