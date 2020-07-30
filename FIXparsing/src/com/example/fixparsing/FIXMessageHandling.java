package com.example.fixparsing;

import java.util.LinkedHashMap;
import java.util.Map;

public class FIXMessageHandling {

    Map<String, String> splitMessage(String input) throws FixMessageParser.ParseException {
        Map<String, String> tagvalue = new LinkedHashMap<>();
        String[] arrsplit = input.split("\002");
        for(String strsplit : arrsplit){
            if(hasUniqueEqual(strsplit)){
                String[] arrSplitEqual = strsplit.split("=");
                tagvalue.put(arrSplitEqual[0],arrSplitEqual[1]);
            }else{
                throw new FixMessageParser.ParseException(input);
            }
        }
        return tagvalue;
    }

    private boolean hasUniqueEqual(String s){
        int count = 0;
        char[] sarray = s.toCharArray();
        for(char ss : sarray){
            if(ss == '='){
                count++;
            }
        }
        if(count==1)
            return true;
        return false;
    }


}
