package com.example.fixparsing;

import java.util.LinkedHashMap;
import java.util.Map;

public class FIXMessageHandling {

    static Map<String, String> splitMessage(String input) throws FixMessageParser.ParseException {
        Map<String, String> tagvalue = new LinkedHashMap<>();
        StringBuilder splitSymbol = new StringBuilder();
        splitSymbol.append("\\").append(getSplitSymbol(input));
        String[] arrsplit = input.split(splitSymbol.toString());

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

    static String getSplitSymbol(String input) throws FixMessageParser.ParseException {
        String splitSymbol = "";
        if (input.length() >= 11 && input.charAt(5) == 'T') {
            int index = 10;
            while (index < input.length() && !(input.charAt(index) == '9')) {
                index++;
            }
            splitSymbol = input.substring(10, index);
        } else if (input.length() >= 10 && input.charAt(5) == '.') {
            int index = 9;
            while (index < input.length() && !(input.charAt(index) == '9')) {
                index++;
            }
            splitSymbol = input.substring(9, index);
        } else {
            throw new FixMessageParser.ParseException(input);
        }
        return splitSymbol;
    }


}
