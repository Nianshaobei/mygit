package com.example.fixparsing;

import java.io.IOException;
import javax.json.*;

public class JsonFixMessageWriter implements FixMessageWriter {
    private JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

    @Override
    public void write(String key, String val) throws IOException {
        jsonBuilder.add(key,val);
    }

    JsonObject getObject(){
        JsonObject jsonObject = jsonBuilder.build();
        return jsonObject;
    }

    String getFormatJson(){
        String jsonString = JsonFormatUtils.formatJson(getObject().toString());
        return jsonString;
    }

}
