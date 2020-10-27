package com.u8.darts.webapp.data;

import java.io.Closeable;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Model implements Closeable {

    //TODO add file logic
    //TODO add Jython thread with actual created model

    @JsonIgnore
    public final ObjectMapper MAPPER = new ObjectMapper();

    public final String id;
    public final String model;
    public final ObjectNode parameters;

    @JsonCreator
    public Model(@JsonProperty("id") String id,
                 @JsonProperty("model") String model,
                 @JsonProperty("parameters") ObjectNode parameters) {
        this.id = generateUUIDIfNotSet(id);
        this.model = model;
        this.parameters = parameters;
    }

    @Override
    public String toString() {

        return "Event{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", parameters=" + parameters +
                "}";
    }

    private static String generateUUIDIfNotSet(String id) {
        return (id != null) ? id : UUID.randomUUID().toString();
    }

    public void close() {}
}
