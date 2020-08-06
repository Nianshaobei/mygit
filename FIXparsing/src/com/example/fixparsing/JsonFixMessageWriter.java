package com.example.fixparsing;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.IOException;

public class JsonFixMessageWriter implements FixMessageWriter {

    private JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

    @Override
    public void write(final String key, final String val) throws IOException {
        jsonBuilder.add(key, val);
    }

    JsonFixMessageWriter(JsonObjectBuilder jsonBuilder) {

        this.jsonBuilder = jsonBuilder;
    }

}
