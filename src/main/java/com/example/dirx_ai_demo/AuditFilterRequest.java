package com.example.dirx_ai_demo;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.util.List;

public record AuditFilterRequest(
        @JsonPropertyDescription("List of usernames to include/exclude. Empty or null if not specified.")
        List<String> usernames,

        @JsonPropertyDescription("If true, the usernames list means 'NOT IN' (exclude these users). If false or null, it means 'IN' (include).")
        Boolean usernamesExcluded,

        @JsonPropertyDescription("List of event types (e.g., DELETE, CREATE, UPDATE) to include/exclude. Empty or null if not specified.")
        List<String> eventTypes,

        @JsonPropertyDescription("If true, the eventTypes list means 'NOT IN' (exclude these events). If false or null, it means 'IN' (include).")
        Boolean eventTypesExcluded,

        @JsonPropertyDescription("List of locations (e.g., Brno, Praha) to include/exclude. Empty or null if not specified.")
        List<String> locations,

        @JsonPropertyDescription("If true, the locations list means 'NOT IN' (exclude these locations). If false or null, it means 'IN' (include).")
        Boolean locationsExcluded
) {}