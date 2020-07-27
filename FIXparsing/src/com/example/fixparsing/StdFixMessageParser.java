package com.example.fixparsing;

import javax.annotation.Nullable;
import java.util.*;

class StdFixMessageParser implements FixMessageParser {

    StdFixTag8Translator stdFixTag8Translator = new StdFixTag8Translator();
    StdFixTag9Translator stdFixTag9Translator = new StdFixTag9Translator();
    StdFixTag10Translator stdFixTag10Translator = new StdFixTag10Translator();
    StdFixTag11Translator stdFixTag11Translator = new StdFixTag11Translator();
    StdFixTag34Translator stdFixTag34Translator = new StdFixTag34Translator();
    StdFixTag35Translator stdFixTag35Translator = new StdFixTag35Translator();
    StdFixTag38Translator stdFixTag38Translator = new StdFixTag38Translator();
    StdFixTag40Translator stdFixTag40Translator = new StdFixTag40Translator();
    StdFixTag49Translator stdFixTag49Translator = new StdFixTag49Translator();
    StdFixTag52Translator stdFixTag52Translator = new StdFixTag52Translator();
    StdFixTag54Translator stdFixTag54Translator = new StdFixTag54Translator();
    StdFixTag56Translator stdFixTag56Translator = new StdFixTag56Translator();
    StdFixTag60Translator stdFixTag60Translator = new StdFixTag60Translator();
    StdFixTag98Translator stdFixTag98Translator = new StdFixTag98Translator();
    StdFixTag108Translator stdFixTag108Translator = new StdFixTag108Translator();

    @Override
    public void parse(
            @Nullable final String input,
            final StringBuilder output,
            final Map<Integer, FixTagTranslator> customTagTranslators
    ) throws ParseException {
        // TODO add implementation here
        customTagTranslators.put(8, stdFixTag8Translator);
        customTagTranslators.put(9, stdFixTag9Translator);
        customTagTranslators.put(10, stdFixTag10Translator);
        customTagTranslators.put(11, stdFixTag11Translator);
        customTagTranslators.put(34, stdFixTag34Translator);
        customTagTranslators.put(35, stdFixTag35Translator);
        customTagTranslators.put(38, stdFixTag38Translator);
        customTagTranslators.put(40, stdFixTag40Translator);
        customTagTranslators.put(49, stdFixTag49Translator);
        customTagTranslators.put(52, stdFixTag52Translator);
        customTagTranslators.put(54, stdFixTag54Translator);
        customTagTranslators.put(56, stdFixTag56Translator);
        customTagTranslators.put(60, stdFixTag60Translator);
        customTagTranslators.put(98, stdFixTag98Translator);
        customTagTranslators.put(108, stdFixTag108Translator);

        FIXMessageHandling handling = new FIXMessageHandling();
        Map<String, String> tagvalue = handling.splitMessage(input);

        for(String key : tagvalue.keySet()){
            String tagTrans, valueTrans,tv;
            ParsedMessage parsedMessage = new ParsedMessage();
            int keyint = Integer.parseInt(key);

            tagTrans = customTagTranslators.get(keyint).getName();
            valueTrans = customTagTranslators.get(keyint).translateValue(tagvalue.get(key));

            parsedMessage.setTagParsing(tagTrans);
            parsedMessage.setValueParsing(valueTrans);

            tv = parsedMessage.getTagParsing() + ":" + parsedMessage.getValueParsing();
            output.append(tv).append("\n");
        }
        output.deleteCharAt(output.length()-1);
    }

}
