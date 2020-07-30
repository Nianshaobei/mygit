package com.example.fixparsing;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;


import com.google.common.collect.Maps;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

public class XMLFixMessageParser{

    public Map<Integer,FixTagTranslator> XMLParser(String XMLpath) {

        Map<Integer,FixTagTranslator> map = Maps.newHashMap();

        try{
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(XMLpath));
            Element root = document.getRootElement();

            List<Element> tagList = root.elements();
            for(Element e : tagList){
                int tagint;
                FixTagTranslator fixTagTranslator;
                Attribute tag = e.attribute("id");
                String tagTrans = e.attribute("trans").getValue();

                try{
                    tagint = Integer.parseInt(tag.getValue());

                    Iterator<?> it = e.elementIterator();
                    if(it.hasNext()){
                        List<Element> valueList = e.elements();
                        fixTagTranslator = new FixTagTranslator() {
                            @Override
                            public String getName() {
                                return tagTrans;
                            }

                            @Override
                            public String translateValue(String value) {
                                Map<String, String> valueMap = new HashMap<>();
                                for(Element v : valueList){
                                    Attribute valueAttributeID = v.attribute("id");
                                    Attribute valueAttributeTrans = v.attribute("trans");
                                    if(valueAttributeID.getValue()!=null){
                                        valueMap.put(valueAttributeID.getValue(), valueAttributeTrans.getValue());
                                    }
                                }
                                return valueMap.get(value);
                            }
                        };

                    }else{
                        fixTagTranslator = new FixTagTranslator() {
                            @Override
                            public String getName() {
                                return tagTrans;
                            }

                            @Override
                            public String translateValue(String value) {
                                return value;
                            }
                        };
                    }
                    map.put(tagint, fixTagTranslator);
                }catch (NumberFormatException e1){
                    e1.printStackTrace();
                }

            }
        }catch (DocumentException e){
            e.printStackTrace();
        }

        return map;
    }

}
