package com.kollar.serpmicroservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventParamsDto {

    /**
     * Query string
     */
    private String query;

    private LocationFilter locationFilter;

    private Htichips htichips;

    /**
     * Locations from JSON locations.json
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class LocationFilter {

        private String name;
    }

    /**
     * htichips
     * date:today - Today's Events
     * date:tomorrow - Tomorrow's Events
     * date:week - This Week's Events
     * date:today - Today's Weekend's Events
     * date:next_week - Next Week's Events
     * date:month - This Month's Events
     * date:next_month - Next Month's Events
     * event_type:Virtual-Event - Online Events
     *
     */
    @Getter
    @Setter
    public static class Htichips {

        private String htichips;
    }
}
