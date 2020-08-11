package com.example.fixparsing;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlParserUtils {

    private static Logger logger = Logger.getLogger(XmlParserUtils.class);

    private static Map<Integer, FixTagTranslator> xmlParser(String resource) {
        Map<Integer, FixTagTranslator> map = Maps.newHashMap();

        Document document = Dom4JReaderUtils.getDocument(resource);
        Element root = document.getRootElement();

        List<Element> tagList = root.elements();
        for (Element t : tagList) {
            int tagint;
            FixTagTranslator fixTagTranslator;
            Attribute tag = t.attribute("number");
            String tagtrans = t.attribute("name").getValue();

            try {
                tagint = Integer.parseInt(tag.getValue());

                Iterator<?> it = t.elementIterator();
                if (it.hasNext()) {

                    List<Element> valueList = t.elements();
                    Map<String, String> valueMap = new HashMap<>();
                    for (Element v : valueList) {
                        Attribute valueAttributeID = v.attribute("enum");
                        Attribute valueAttributeTrans = v.attribute("description");
                        valueMap.put(valueAttributeID.getValue(), valueAttributeTrans.getValue());
                    }

                    fixTagTranslator = new FixTagTranslator() {
                        @Override
                        public String getName() {
                            return tagtrans;
                        }

                        @Override
                        public String translateValue(String value) {
                            if (valueMap.get(value) != null) {
                                return valueMap.get(value);
                            }
                            return "Unknown";
                        }
                    };

                } else {

                    fixTagTranslator = new FixTagTranslator() {
                        @Override
                        public String getName() {
                            return tagtrans;
                        }

                        @Override
                        public String translateValue(String value) {
                            return value;
                        }
                    };
                }
                map.put(tagint, fixTagTranslator);
            } catch (NumberFormatException e) {
                logger.error("Failed to convert integer type.",e);
            }

        }
        return map;
    }

    static Map<Integer, FixTagTranslator> loadBuiltinTranslators(String resource) {
        Map<Integer, FixTagTranslator> map = xmlParser(resource);
        return map;
    }

}
