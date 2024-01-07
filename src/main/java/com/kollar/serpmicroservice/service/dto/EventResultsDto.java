package com.kollar.serpmicroservice.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response with events
 */
@Getter
@Setter
public class EventResultsDto {

    private List<EventResult> eventResults;

    @Getter
    @Setter
    public static class EventResult {

        private String title;

        private List<String> address;

        private String link;

        private String description;

        private EventLocationDto eventLocation;

        private EventDateDto eventDate;

        private VenueDto venue;

        private List<TicketInfoDto> ticketInfos;

        private byte[] image;
    }
}
