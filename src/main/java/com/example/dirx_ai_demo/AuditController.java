package com.example.dirx_ai_demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
@Tag(name = "Audit Intelligence Engine", description = "Endpoints for AI-driven log orchestration and filtering")
public class AuditController {

    private final AiAuditService aiAuditService;

    public AuditController(AiAuditService aiAuditService) {
        this.aiAuditService = aiAuditService;
    }

    @GetMapping("/search")
    @Operation(
            summary = "Query audit logs using natural language",
            description = "Pass a natural language prompt (English). AI will parse it into granular database filters."
    )
    public List<AuditLog> searchLogs(
            @Parameter(description = "Natural language prompt, e.g., 'show deleted logs by Peter' or 'all' to reset", example = "show everything outside Brno")
            @RequestParam String prompt
    ) {
        return aiAuditService.searchAuditLogsWithAi(prompt);
    }
}