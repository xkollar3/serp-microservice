package com.kollar.serpmicroservice.api;

import com.kollar.serpmicroservice.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/location")
public class LocationApi {

    private final LocationService locationService;

    @Autowired
    public LocationApi(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/")
    public String locationUuid(@RequestParam String locationString) {
        return locationService.find(locationString);
    }
}
