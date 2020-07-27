package com.example.fixparsing;

public class StdFixTag9Translator implements FixTagTranslator{
    @Override
    public String getName() {
        return "消息体长度";
    }

    @Override
    public String translateValue(String value) throws FixMessageParser.ParseException {
        if ((value.matches("[0-9]+"))&&(Integer.parseInt(value)>0)){
            return value;
        }else{
            throw new FixMessageParser.ParseException(value);
        }
    }
}
