package com.u8.darts.webapp.services;

import com.u8.darts.webapp.configuration.AppConfig;
import com.u8.darts.webapp.data.Model;
import com.u8.darts.webapp.data.TimeseriesEvent;
import org.python.antlr.op.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutionException;


@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ModelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelService.class);
    private static final Set<String> NOT_OVERRIDABLE_PROPERTIES = new HashSet<>();

    private final AppConfig appConfig;
    private final HashMap<String, Model> models;

    @Autowired
    public ModelService(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.models = new HashMap<>();
    }

    @PreDestroy
    public void destroy() {
        models.values().forEach(Model::close);
    }

    @PostConstruct
    private void init() {
        //TODO init blocking queue service
    }

    public void flush() {}

    public void writeEvent(TimeseriesEvent event) {
        //TODO pass along to blocking queue
    }

    public Model getModel(String key) {
        return models.get(key);
    }

    public boolean modelExists(String key) {
        return models.containsKey(key);
    }

    public void registerModel(Model model) {
        if (modelExists(model.id))
            //TODO change this to custom exception
            throw new RuntimeException("Model with id " + model.id + " already exists!");
        models.put(model.id, model);
        LOGGER.info(String.format("Current models: {}", models));
    }
}
