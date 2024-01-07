package com.kollar.serpmicroservice.api;

import com.kollar.serpmicroservice.service.SerpEventService;
import com.kollar.serpmicroservice.service.dto.EventParamsDto;
import com.kollar.serpmicroservice.service.dto.EventResultsDto;
import com.kollar.serpmicroservice.service.dto.SerpResultsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController("/api/events/")
public class SerpEventApi {

    private final SerpEventService serpEventService;

    @Autowired
    public SerpEventApi(SerpEventService serpEventService) {
        this.serpEventService = serpEventService;
    }

    @PostMapping("/list")
    public EventResultsDto events(@Validated @RequestBody EventParamsDto params) throws UnsupportedEncodingException {
        return serpEventService.findAll(params);
    }
}
