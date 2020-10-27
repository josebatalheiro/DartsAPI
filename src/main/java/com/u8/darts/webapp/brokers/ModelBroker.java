package com.u8.darts.webapp.brokers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.u8.darts.webapp.configuration.AppConfig;
import com.u8.darts.webapp.data.Model;
import com.u8.darts.utils.LoggingUtils;
import com.u8.darts.webapp.services.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Repository
public class ModelBroker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelBroker.class);
    private static final ObjectReader OBJECT_READER;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        OBJECT_READER = objectMapper.readerFor(Model.class);
    }

    private final AppConfig appConfig;
    private final ModelService modelService;

    @Autowired
    public ModelBroker(AppConfig appConfig, ModelService modelService) {
        this.appConfig = appConfig;
        this.modelService = modelService;
    }

    public Model logModel(HttpServletRequest request, Model model) {
        LoggingUtils.logTrace(LOGGER, request, ModelBroker.class, "logBrokerCreation");

        return model;
    }

    public Model registerModel(HttpServletRequest request, Model model) {
        LoggingUtils.logEndpointRequest(LOGGER, request, ModelBroker.class + ":registerModel");

        modelService.registerModel(model);

        return model;
    }
}
