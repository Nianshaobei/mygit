package com.example.fixparsing;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.util.Collections;

class FixMessageParserTest {
    private FixMessageParser parser;
    static final String NEW_ORDER_SINGLE = "8=FIX.4.4\0029=100\00235=D\00211=12345\00240=1\00254=1\00238=5000\00260=2003061501:14:49\00210=127";

    static final String LOGON = "8=FIX.4.2\0029=73\00235=A\00234=1\00249=CLIENT\00252=20181119-10:42:48.768\00256=SERVER\00298=0\002108=30\002141=Y\00210=208";
    static final String NEW_ORDER_SINGLE_1 = "8=FIX.4.2\0029=251\00235=D\00249=AFUNDMGR\00256=ABROKER\00234=2\00252=20030615-01:14:49\002" +
            "11=12345\0021=111111\00263=0\00264=20030621\00221=3\002110=1000\002111=50000\00255=IBM\00248=459200101\00222=1" +
            "\00254=1\00260=2003061501:14:49\00238=5000\00240=1\00244=15.75\00215=USD\00259=0\00210=127";

    @BeforeEach
    void setUp() {
        parser = new StdFixMessageParser();
    }

    @Test
    void testFixNewOrderSingletoJson() throws IOException, FixMessageParser.ParseException {
        final String input = NEW_ORDER_SINGLE;

        final String expected = "\n" +
                "{\n" +
                "    \"BeginString\":\"FIX.4.4\",\n" +
                "    \"BodyLength\":\"100\",\n" +
                "    \"MsgType\":\"ORDER_SINGLE\",\n" +
                "    \"ClOrdID\":\"12345\",\n" +
                "    \"OrdType\":\"MARKET\",\n" +
                "    \"Side\":\"BUY\",\n" +
                "    \"OrderQty\":\"5000\",\n" +
                "    \"TransactTime\":\"2003061501:14:49\",\n" +
                "    \"CheckSum\":\"127\"\n" +
                "}";

        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        JsonFixMessageWriter writer = new JsonFixMessageWriter(jsonBuilder);
        String resource = "src\\FIX.xml";
        parser.parse(input, writer, Collections.emptyMap(),resource);
        JsonObject jsonObject = jsonBuilder.build();
        String jsonString = JsonFormatUtils.jsonObject2prettyString(jsonObject);
        Truth.assertThat(jsonString).isEqualTo(expected);
    }

    @Test
    void testFixNewOrderSingletoString() throws Exception {
        final String input = NEW_ORDER_SINGLE;

        //Dom4JReaderUtils.resource = "src\\FIX.xml";

        final String expected = "BeginString => FIX.4.4\r\n" +
                "BodyLength => 100\r\n" +
                "MsgType => ORDER_SINGLE\r\n" +
                "ClOrdID => 12345\r\n" +
                "OrdType => MARKET\r\n" +
                "Side => BUY\r\n" +
                "OrderQty => 5000\r\n" +
                "TransactTime => 2003061501:14:49\r\n" +
                "CheckSum => 127\r\n";

        StringBuilder sb = new StringBuilder();
        String resource = "src\\FIX.xml";
        SimpleFixMessageWriter writer = new SimpleFixMessageWriter(sb);
        parser.parse(input, writer, Collections.emptyMap(),resource);

        Truth.assertThat(sb.toString()).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {NEW_ORDER_SINGLE, NEW_ORDER_SINGLE_1, LOGON})
    void testSplitMessage(String message) throws FixMessageParser.ParseException {
        System.out.println(FIXMessageHandling.splitMessage(message));
    }

    @Test
    // FIXME fix test error and make it run
    void testInvalidFixMessages() throws Exception {
        final String[] input = {
                "123",
                "8==2"
        };

        for (String s : input) {

            StringBuilder sb = new StringBuilder();
            SimpleFixMessageWriter writer = new SimpleFixMessageWriter(sb);
            String resource = "src\\FIX.xml";
            Assertions.assertThrows(
                    FixMessageParser.ParseException.class,
                    () -> parser.parse(s, writer, Collections.emptyMap(), resource)
            );
        }

    }


}
