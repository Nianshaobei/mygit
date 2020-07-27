package com.example.fixparsing;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;


import org.dom4j.*;
import org.dom4j.io.SAXReader;

public class XMLFixMessageParser implements FixMessageParser{
    @Override
    public void parse(
            @Nullable final String input,
            final StringBuilder output
    ) throws ParseException {

        FIXMessageHandling handling = new FIXMessageHandling();
        Map<String, String> tagvalue = handling.splitMessage(input);

        String tagTrans,valueTrans = "",tv;
        ParsedMessage parsedMessage = new ParsedMessage();
        
        try{
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File("src/FIX.xml"));
            Element root = document.getRootElement();
            for(String key: tagvalue.keySet()){
                List<Element> tagList = root.elements();

                for(Element e : tagList){
                    Attribute tag = e.attribute("id");
                    if(key.equals(tag.getValue())){
                        tagTrans = e.attribute("trans").getValue();
                        Iterator<?> it=e.elementIterator();
                        if(it.hasNext()){
                            List<Element> valueList = e.elements();
                            for(Element v : valueList){
                                Attribute value = v.attribute("id");
                                if(tagvalue.get(key).equals(value.getValue())){
                                    valueTrans = v.attribute("trans").getValue();
                                }
                            }
                        }else{
                            valueTrans = tagvalue.get(key);
                        }
                        parsedMessage.setTagParsing(tagTrans);
                        parsedMessage.setValueParsing(valueTrans);
                        
                        tv = parsedMessage.getTagParsing() + ":" + parsedMessage.getValueParsing();
                        output.append(tv).append("\n");
                    }
                }
            }
            output.deleteCharAt(output.length()-1);
        }catch (DocumentException e){
            e.printStackTrace();
        }

    }
}
