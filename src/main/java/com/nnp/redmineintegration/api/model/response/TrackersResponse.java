package com.nnp.redmineintegration.api.model.response;

import java.util.List;

public record TrackersResponse(
        List<Tracker> trackers
) {
    public record Tracker(
            int id,
            String name,
            DefaultStatus default_status,
            String description,
            List<String> enabled_standard_fields
    ) {}

    public record DefaultStatus(
            int id,
            String name
    ) {}
}