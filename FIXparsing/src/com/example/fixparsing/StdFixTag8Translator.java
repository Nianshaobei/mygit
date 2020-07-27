package com.example.fixparsing;

import java.util.List;

public class StdFixTag8Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "协议版本";
    }

    @Override
    public String translateValue(String value) throws FixMessageParser.ParseException {
        switch (value) {
            case "FIX.4.0":
                return "FIX.4.0";
            case "FIX.4.1":
                return "FIX.4.1";
            case "FIX.4.2":
                return "FIX.4.2";
            case "FIX.4.3":
                return "FIX.4.3";
            case "FIX.4.4":
                return "FIX.4.4";
            case "FIX.5.0":
                return "FIX.5.0";
            default:
                throw new FixMessageParser.ParseException(value);
        }
    }
}
