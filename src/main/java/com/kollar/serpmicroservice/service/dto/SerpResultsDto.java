package com.kollar.serpmicroservice.service.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Dto returned by serp api
 */
@Getter
@Setter
public class SerpResultsDto {

    @JsonAlias("events_results")
    private List<SerpEventResult> eventResults;

    @Getter
    @Setter
    public static class SerpEventResult {

        private String title;

        private List<String> address;

        private String link;

        private String description;

        private EventLocationDto eventLocation;

        private EventDateDto date;

        private VenueDto venue;

        private List<TicketInfoDto> tickets;

        private String thumbnail;
    }
}