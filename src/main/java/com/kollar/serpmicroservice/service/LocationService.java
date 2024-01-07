package com.kollar.serpmicroservice.service;

import org.springframework.stereotype.Service;

/**
 * Service for precise location resolution
 */
@Service
public class LocationService {

    /**
     * Resolve location UUID from locations.json, if found return this UUID,
     * in case it is not found return original query string back
     *
     * @param locationString String of location
     * @return String either UUID or original
     */
    public String find(String locationString) {
        return "";
    }
}
