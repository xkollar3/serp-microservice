package com.kollar.serpmicroservice.service;

import com.kollar.serpmicroservice.service.dto.EventParamsDto;
import com.kollar.serpmicroservice.service.dto.EventResultsDto;
import com.kollar.serpmicroservice.service.dto.SerpResultsDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for event lookup using the serp api
 */
@Service
public class SerpEventService {

    @Value("${serp.api.url}")
    @NonNull
    @Setter
    private String apiUrl;

    @Value("${serp.api.key}")
    @NonNull
    @Setter
    private String apiKey;

    /**
     * Method calls the serp events API and processes the results
     *
     * @param params EventParamsDto search params for events
     * @return EventParamsDto results of serps search
     */
    public EventResultsDto findAll(EventParamsDto params) {
        Map<String, String> searchParams = createSearchParams(params);
        String queryParams = ParamsStringBuilder.getParamsString(searchParams);

        HttpEntity<Void> http = new HttpEntity<>(new HttpHeaders());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SerpResultsDto> result = restTemplate.exchange(apiUrl + "?" + queryParams, HttpMethod.GET, http, SerpResultsDto.class);

        if (result.getStatusCode().equals(HttpStatus.OK)) {
            return mapResults(result.getBody());
        } else {
            // todo: handle errors better!
            throw new RuntimeException("Something went wrong fetching from serp");
        }
    }

    private Map<String, String> createSearchParams(EventParamsDto params) {
        Map<String, String> searchParams = new HashMap<>();

        if (params.getQuery() != null) {
            searchParams.put("q", params.getQuery());
        }
        searchParams.put("engine", "google_events");
        searchParams.put("google_domain", "google.com");
        if (params.getLocationFilter() != null && params.getLocationFilter().getName() != null) {
            searchParams.put("location", params.getLocationFilter().getName());
        }
        if (params.getHtichips() != null && params.getHtichips().getHtichips() != null) {
            searchParams.put("htichips", params.getHtichips().getHtichips());
        }
        searchParams.put("api_key", apiKey);
        return searchParams;
    }

    private EventResultsDto mapResults(SerpResultsDto resultsDto) {
        if (resultsDto == null) {
            throw new IllegalArgumentException("Null value passed as resultsDto");
        }

        EventResultsDto eventResultsDto = new EventResultsDto();
        eventResultsDto.setEventResults(new ArrayList<>());
        if (resultsDto.getEventResults() != null) {
            resultsDto.getEventResults().forEach(result -> {
                EventResultsDto.EventResult eventResult = new EventResultsDto.EventResult();
                eventResult.setTitle(result.getTitle());
                eventResult.setAddress(result.getAddress());
                eventResult.setLink(result.getLink());
                eventResult.setDescription(result.getDescription());
                eventResult.setEventLocation(result.getEventLocation());
                eventResult.setEventDate(result.getDate());
                eventResult.setVenue(result.getVenue());
                eventResult.setTicketInfos(result.getTickets());
                eventResult.setImage(fetchImage(result.getThumbnail()));
                eventResultsDto.getEventResults().add(eventResult);
            });
        }

        return eventResultsDto;
    }

    private byte[] fetchImage(String url) {
        if (url == null) {
            return null;
        }

        try (BufferedInputStream in = new BufferedInputStream(URI.create(url).toURL().openStream())) {
            return in.readAllBytes();
        } catch (Exception e) {
            //todo: handle this better
            throw new RuntimeException(e);
        }
    }
}
