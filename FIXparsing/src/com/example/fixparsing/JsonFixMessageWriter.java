package com.example.fixparsing;

import java.io.IOException;
import com.alibaba.fastjson.JSONObject;

public class JsonFixMessageWriter implements FixMessageWriter {
    private final JSONObject object = new JSONObject();

    @Override
    public void write(String key, String val) throws IOException {
        object.put(key, val);
    }

//    public JSONObject getObject(){
//        return object;
//    }

    public String getPrettyJson(){
        return CommonUtils.prettyJson(object.toJSONString());
    }
}
