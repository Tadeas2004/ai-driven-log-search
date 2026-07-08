package com.example.dirx_ai_demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AiAuditService {

    private final ChatClient chatClient;
    private final AuditLogRepository auditLogRepository;

    public AiAuditService(ChatClient.Builder chatClientBuilder, AuditLogRepository auditLogRepository) {
        // Dynamically inject the current system date so the model always knows the true 'today'
        String systemInstructions = "You are an expert AI assistant converting natural " +
                "language user prompts into structured audit log filters. " +
                "Extract lists of values and detect if the user wants to exclude " +
                "them (e.g., 'outside', 'except', 'everything but deleted'). "
                + "Current system date for reference: " + LocalDate.now() + ".";

        this.chatClient = chatClientBuilder
                .defaultSystem(systemInstructions)
                .build();
        this.auditLogRepository = auditLogRepository;
    }

    public List<AuditLog> searchAuditLogsWithAi(String userPrompt) {
        if ("all".equalsIgnoreCase(userPrompt)) {
            return auditLogRepository.findAll();
        }

        try {
            AuditFilterRequest filter = chatClient.prompt()
                    .user(userPrompt)
                    .call()
                    .entity(AuditFilterRequest.class);

            if (filter == null) {
                System.out.println("--- AI fallback: Model returned null filter configuration ---");
                return auditLogRepository.findAll();
            }

            System.out.println("--- AI analyzed prompt and generated advanced filter: ---");
            System.out.println(filter);

            // Database filter based on the AI response
            return auditLogRepository.findAll().stream()
                    .filter(log -> {
                        if (filter.usernames() == null || filter.usernames().isEmpty()) return true;
                        boolean match = filter.usernames().stream().anyMatch(name -> name.equalsIgnoreCase(log.getUsername()));
                        boolean isExcluded = filter.usernamesExcluded() != null && filter.usernamesExcluded();
                        return isExcluded != match;
                    })
                    .filter(log -> {
                        if (filter.eventTypes() == null || filter.eventTypes().isEmpty()) return true;
                        boolean match = filter.eventTypes().stream().anyMatch(type -> type.equalsIgnoreCase(log.getEventType()));
                        boolean isExcluded = filter.eventTypesExcluded() != null && filter.eventTypesExcluded();
                        return isExcluded != match;
                    })
                    .filter(log -> {
                        if (filter.locations() == null || filter.locations().isEmpty()) return true;
                        boolean match = filter.locations().stream().anyMatch(loc -> loc.equalsIgnoreCase(log.getLocation()));
                        boolean isExcluded = filter.locationsExcluded() != null && filter.locationsExcluded();
                        return isExcluded != match;
                    })
                    .toList();

        } catch (Exception e) {
            System.err.println("--- CRITICAL: AI Orchestration failed or timed out! Reason: " + e.getMessage() + " ---");
            System.out.println("--- Applying safe fallback: Returning all unfiltered records from PostgreSQL. ---");

            return auditLogRepository.findAll();
        }
    }
}