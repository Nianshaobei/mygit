package com.example.fixparsing;

import java.util.LinkedHashMap;
import java.util.Map;

public class FIXMessageHandling {

    static Map<String, String> splitMessage(String input) throws FixMessageParser.ParseException {
        Map<String, String> tagvalue = new LinkedHashMap<>();
        String splitSymbol = "";
        if (input.length() >= 10 && !Character.isDigit(input.charAt(9))) {
            splitSymbol = input.substring(9, 10);
        } else if (input.length() >= 11 && !Character.isDigit(input.charAt(10))) {
            splitSymbol = input.substring(10, 11);
        } else {
            throw new FixMessageParser.ParseException(input);
        }
        String[] arrsplit = input.split(splitSymbol);

        for (String strsplit : arrsplit) {
            String[] arrSplitEqual = strsplit.split("=");
            if (arrSplitEqual.length == 2) {
                tagvalue.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                throw new FixMessageParser.ParseException(input);
            }
        }
        return tagvalue;
    }

}
