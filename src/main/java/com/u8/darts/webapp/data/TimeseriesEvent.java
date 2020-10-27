package com.u8.darts.webapp.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeseriesEvent {

    @JsonIgnore
    public final ObjectMapper MAPPER = new ObjectMapper();

    public final String id;
    public final String model;
    public final ObjectNode payload;


    @JsonCreator
    public TimeseriesEvent(@JsonProperty("id") String id,
                           @JsonProperty("model") String model,
                           @JsonProperty("payload") ObjectNode payload) {
        this.id = generateUUIDIfNotSet(id);
        this.model = model;
        this.payload = payload;

    }

    @Override
    public String toString() {

        return "Event{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", payload=" + payload +
                "}";
    }

    private static String generateUUIDIfNotSet(String id) {
        return (id != null) ? id : UUID.randomUUID().toString();
    }

}
