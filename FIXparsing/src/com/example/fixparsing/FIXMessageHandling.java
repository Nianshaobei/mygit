package com.example.fixparsing;

import java.util.LinkedHashMap;
import java.util.Map;

public class FIXMessageHandling {

    static Map<String, String> splitMessage(String input) throws FixMessageParser.ParseException {
        Map<String, String> tagvalue = new LinkedHashMap<>();
        String[] arrsplit = input.split("\002");

        for(String strsplit : arrsplit){
            String[] arrSplitEqual = strsplit.split("=");
            if(arrSplitEqual.length==2){
                tagvalue.put(arrSplitEqual[0],arrSplitEqual[1]);
            }else{
                throw new FixMessageParser.ParseException(input);
            }
        }
        return tagvalue;
    }

}
