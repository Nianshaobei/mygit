package com.example.fixparsing;

public class StdFixTag40Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "订单类型";
    }

    @Override
    public String translateValue(String value) throws FixMessageParser.ParseException {
        switch (value){
            case "1" : return "市价订单";
            case "2" : return "限价订单";
            default : throw new FixMessageParser.ParseException(value);
        }
    }
}
