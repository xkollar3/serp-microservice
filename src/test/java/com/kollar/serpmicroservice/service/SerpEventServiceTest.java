package com.kollar.serpmicroservice.service;

import com.kollar.serpmicroservice.service.dto.EventParamsDto;
import com.kollar.serpmicroservice.service.dto.EventResultsDto;
import com.nimbusds.common.contenttype.ContentType;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"serp.api.key=notneeded", "serp.api.url=http://test.com"})
@ContextConfiguration(classes = {SerpEventService.class, MockWebServer.class})
class SerpEventServiceTest {

    @Autowired
    private SerpEventService serpEventService;

    @Autowired
    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() {
        String baseUrl = mockWebServer.url("/").toString();
        serpEventService.setApiUrl(baseUrl);
    }

    @Test
    public void testGetEvents() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(SerpEventResponse.response)
                .addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON));

        EventParamsDto eventParamsDto = new EventParamsDto();
        eventParamsDto.setLocationFilter(new EventParamsDto.LocationFilter("events in Austin"));
        EventResultsDto results = serpEventService.findAll(eventParamsDto);

        Assertions.assertNotNull(results.getEventResults());
        Assertions.assertFalse(results.getEventResults().isEmpty());

        EventResultsDto.EventResult eventResult = results.getEventResults().get(0);
        Assertions.assertEquals("Austin Blues Festival: Two-Day Pass", eventResult.getTitle());
        Assertions.assertEquals("Sat, Apr 27 – Sun, Apr 28", eventResult.getEventDate().getWhen());
        Assertions.assertEquals(2, eventResult.getAddress().size());
        Assertions.assertEquals("https://www.google.com/search?gl=us&hl=en&sourceid=chrome&q=events+in+Austin&ibp=htl;events&rciv=evn&sa=X&ved=2ahUKEwi8g5OzosmDAxWAiO4BHX0EBIsQ5bwDegQIbxAB#fpstate=tldetail&htidocid=L2F1dGhvcml0eS9ob3Jpem9uL2NsdXN0ZXJlZF9ldmVudC8yMDI0LTA0LTI3fF8xMjkxNjA0NzYxNzMwMTk5MzQwOA%3D%3D&htivrt=events&mid=/g/11l5ltvsc6", eventResult.getLink());
    }
}