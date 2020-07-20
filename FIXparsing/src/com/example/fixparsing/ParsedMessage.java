package com.example.fixparsing;

public class ParsedMessage {

    private String tagParsing;
    private String valueParsing;

    public ParsedMessage(){}

    public ParsedMessage(String tagParsing, String valueParsing){
        this.tagParsing = tagParsing;
        this.valueParsing = valueParsing;
    }

    public void setTagParsing(String tagParsing){
        this.tagParsing = tagParsing;
    }
    public void setValueParsing(String valueParsing){
        this.valueParsing = valueParsing;
    }

    public String getTagParsing() {
        return tagParsing;
    }

    public String getValueParsing() {
        return valueParsing;
    }
}
