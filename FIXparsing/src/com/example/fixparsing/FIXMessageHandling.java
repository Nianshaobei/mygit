package com.example.fixparsing;

import java.util.LinkedHashMap;
import java.util.Map;

public class FIXMessageHandling {

    /**
     * 把输入的fixmessage拆分成键值对，并将键值对存入linkedHashMap中
     */
    public Map<String, String> TagValue(String fixmessage){
        Map<String, String> tagvalue = new LinkedHashMap<>();  //保证插入顺序
        String[] arrsplit = fixmessage.split("\002");
        for(String strsplit : arrsplit){
            String[] arrSplitEqual = null;
            arrSplitEqual = strsplit.split("=");
            tagvalue.put(arrSplitEqual[0],arrSplitEqual[1]);
        }
        return tagvalue;
    }

    public void Translate(String fixmessage){
        Map<String, String> tagvalue = TagValue(fixmessage);
        MapDef mapDef = new MapDef();

        for(String key : tagvalue.keySet()){
            String tagTrans, valueTrans, tvmark;
            ParsedMessage parsedMessage = new ParsedMessage();
            if(mapDef.getTagFlag(key)){
                tagTrans = mapDef.getTagMessageMap().get(key);
                tvmark = key + tagvalue.get(key);
                //System.out.println(tvmark);
                valueTrans = mapDef.getValueMessageMap().get(tvmark);
            }else{
                tagTrans = mapDef.getTagMessageMap().get(key);
                valueTrans = tagvalue.get(key);
            }
            parsedMessage.setTagParsing(tagTrans);
            parsedMessage.setValueParsing(valueTrans);
            System.out.println(parsedMessage.getTagParsing()+"为"+parsedMessage.getValueParsing());
        }

    }

}
