package com.example.fixparsing;

public class StdFixTag98Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "加密方式";
    }

    @Override
    public String translateValue(String value) throws FixMessageParser.ParseException {
        switch (value){
            case "0" : return "无/其他";
            case "1" : return "PKCS";
            case "2" : return "DES";
            case "3" : return "PKCS/DES";
            case "4" : return "PGP/DES";
            default : throw new FixMessageParser.ParseException(value);
        }
    }
}
