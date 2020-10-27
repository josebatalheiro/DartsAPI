package com.u8.darts.webapp.brokers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.u8.darts.utils.LoggingUtils;
import com.u8.darts.webapp.configuration.AppConfig;
import com.u8.darts.webapp.data.Model;
import com.u8.darts.webapp.data.TimeseriesEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Repository
public class TimeseriesEventBroker {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeseriesEventBroker.class);
    private static final ObjectReader OBJECT_READER;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        OBJECT_READER = objectMapper.readerFor(Model.class);
    }

    private final AppConfig appConfig;

    @Autowired
    public TimeseriesEventBroker(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public TimeseriesEvent logEvent(HttpServletRequest request, TimeseriesEvent event) {
        LoggingUtils.logTrace(LOGGER, request, TimeseriesEventBroker.class, "logBrokerCreation");

        return event;
    }

    public TimeseriesEvent registerEvent(HttpServletRequest request, TimeseriesEvent event) {
        LoggingUtils.logEndpointRequest(LOGGER, request, TimeseriesEventBroker.class + ":registerEvent");

        //TODO pass event to file writer

        return event;
    }

    public void registerStream(HttpServletRequest request) {
        LoggingUtils.logEndpointRequest(LOGGER, request, TimeseriesEventBroker.class + ":registerEventsFromStream");
        long startTime = System.currentTimeMillis();

        try(BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            //TODO place events to proper csv file
        } catch(IOException e) {
            LOGGER.error("Failed to read stream of events.");
        } finally {
            LOGGER.info("{} Took {} ms", LoggingUtils.logPrefix(request), (System.currentTimeMillis() - startTime));
        }
    }
}
