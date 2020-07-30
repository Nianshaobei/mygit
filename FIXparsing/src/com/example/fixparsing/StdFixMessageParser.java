package com.example.fixparsing;

import com.google.common.collect.Maps;
import org.dom4j.DocumentException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

class StdFixMessageParser implements FixMessageParser {

    private static final XMLFixMessageParser xmlFixMessageParser = new XMLFixMessageParser();

    private Map<Integer, FixTagTranslator> loadBuiltinTranslators() {
        final Map<Integer,FixTagTranslator> map = xmlFixMessageParser.XMLParser("src/FIX.xml");
        return map;
    }

    @Override
    public void parse(
            @Nullable final String input,
            final FixMessageWriter writer,
            final Map<Integer, FixTagTranslator> customTagTranslators
    ) throws ParseException, IOException {
        // TODO add implementation here

        final Map<Integer,FixTagTranslator> builtinTranslators = loadBuiltinTranslators();
        builtinTranslators.putAll(customTagTranslators);

        FIXMessageHandling handling = new FIXMessageHandling();
        Map<String, String> tagvalue = handling.splitMessage(input);

        for(Map.Entry<String, String> entry : tagvalue.entrySet()){
            final String key = entry.getKey();
            final String val = entry.getValue();
            String tagTrans, valueTrans;
            ParsedMessage parsedMessage = new ParsedMessage();

            try {
                int keyint = Integer.parseInt(key);

                if(builtinTranslators.get(keyint)!=null){
                    tagTrans = builtinTranslators.get(keyint).getName();
                    valueTrans = builtinTranslators.get(keyint).translateValue(val);

                    parsedMessage.setTagParsing(tagTrans);
                    parsedMessage.setValueParsing(valueTrans);
                }

            }catch (NumberFormatException e){
                e.printStackTrace();
            }

            writer.write(parsedMessage.getTagParsing(),parsedMessage.getValueParsing());
        }
    }

}
