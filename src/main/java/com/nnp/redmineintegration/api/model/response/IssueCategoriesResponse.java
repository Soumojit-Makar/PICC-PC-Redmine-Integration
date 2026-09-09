package com.nnp.redmineintegration.api.model.response;

import java.util.List;

public record IssueCategoriesResponse(
        List<IssueCategory> issue_categories,
        int total_count
) {
    public record IssueCategory(
            int id,
            Project project,
            String name
    ) {}

    public record Project(
            int id,
            String name
    ) {}
}