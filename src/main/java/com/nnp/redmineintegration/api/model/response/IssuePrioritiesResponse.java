package com.nnp.redmineintegration.api.model.response;

import java.util.List;

public record IssuePrioritiesResponse(
        List<IssuePriority> issue_priorities
) {
    public record IssuePriority(
            int id,
            String name,
            boolean is_default,
            boolean active
    ) {}
}
