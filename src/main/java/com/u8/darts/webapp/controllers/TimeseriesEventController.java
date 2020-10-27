package com.u8.darts.webapp.controllers;

import com.u8.darts.webapp.brokers.TimeseriesEventBroker;
import com.u8.darts.webapp.data.TimeseriesEvent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/events")
public class TimeseriesEventController {

    private final TimeseriesEventBroker eventBroker;

    @Autowired
    public TimeseriesEventController(TimeseriesEventBroker eventBroker) {

        this.eventBroker = eventBroker;
    }

    @ApiOperation(value = "Publishes a new event",
            notes = "It is possible to register an event synchronously or asynchronously (default) depending on the " +
                    "request parameter <b>sync</b> being <b>true</b> or <b>false</b>, respectively.")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Event accepted. The registering is done asynchronously"),
            @ApiResponse(code = 204, message = "Event registered. No response body is provided"),
            @ApiResponse(code = 400, message = "Validation failure", response = Error.class),
            @ApiResponse(code = 404, message = "Source does not exist", response = Error.class),
            @ApiResponse(code = 422, message = "Request body failed preconditions checking", response = Error.class)
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @JsonEndpoint(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> registerEvent(HttpServletRequest request, @RequestBody TimeseriesEvent event) {

        eventBroker.registerEvent(request, event);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Log a new event (for testing)",
            notes = "This endpoint is just a dummy and have no side effects other than logging")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Event written.")
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @JsonEndpoint(value = "test", method = RequestMethod.POST)
    public ResponseEntity<Void> logEvent(HttpServletRequest request, @RequestBody TimeseriesEvent event) {
        eventBroker.logEvent(request, event);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Publishes timeseries events as a stream",
            notes = "This endpoint will be able to handle streams of binary data. Example usage: " +
                    "curl -i -X POST --header 'Content-Type: application/octet-stream' --data-binary " +
                    "\"@airplane-data.txt\" 'http://localhost:8080/{context_name}/api/v1/events/stream'")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Events successfully written")
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST, value = "/stream", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerEventsStream(HttpServletRequest request, @RequestParam(defaultValue = "false") boolean details) {
        //TODO proper handling of return
        eventBroker.registerStream(request);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
