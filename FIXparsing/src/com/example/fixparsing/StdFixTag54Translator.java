package com.example.fixparsing;

public class StdFixTag54Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "订单方";
    }

    @Override
    public String translateValue(String value) throws FixMessageParser.ParseException {
        switch (value){
            case "1" : return "买方";
            case "2" : return "卖方";
            case "5" : return "卖空";
            default : throw new FixMessageParser.ParseException(value);
        }
    }
}
