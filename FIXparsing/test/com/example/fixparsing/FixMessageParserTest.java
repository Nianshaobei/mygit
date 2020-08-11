package com.example.fixparsing;

import com.google.common.collect.ImmutableMap;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

class FixMessageParserTest {
    private FixMessageParser parser;
    private static final String NEW_ORDER_SINGLE = "8=FIX.4.4\0029=100\00235=D\00211=12345\00240=1\00254=1\00238=5000\00260=2003061501:14:49\00210=127";

    private static final String LOGON = "8=FIX.4.2\0029=73\00235=A\00234=1\00249=CLIENT\00252=20181119-10:42:48.768\00256=SERVER\00298=0\002108=30\002141=Y\00210=208";
    private static final String NEW_ORDER_SINGLE_1 = "8=FIX.4.2\0029=251\00235=D\00249=AFUNDMGR\00256=ABROKER\00234=2\00252=20030615-01:14:49\002" +
            "11=12345\0021=111111\00263=0\00264=20030621\00221=3\002110=1000\002111=50000\00255=IBM\00248=459200101\00222=1" +
            "\00254=1\00260=2003061501:14:49\00238=5000\00240=1\00244=15.75\00215=USD\00259=0\00210=127";
    private static final String INPUT_1 = "8=FIXT.1.1^A9=402^A35=8^A49=FGW^A56=123^A34=13939^A52=20171013-10:56:41.008^A1128=9^A115=21146^A17=E0XF1VrQVLRK^A11=20171013162640993^A37=O0XF1ahT51rY^A442=1^A150=C^A39=C^A58=Expired (Price to convert breaches Price Bands)^A151=0^A14=0^A55=COTTON14OCT17CE17414FNOV17^A453=3^A448=21146^A447=D^A452=53^A448=00923^A447=D^A452=1^A448=123451234512345^A447=D^A452=44^A40=K^A59=0^A54=1^A38=50^A1138=0^A60=20171013-10:56:40.989^A1180=1^A30001=1^A581=3^A22009=28649^A10=074";
    private static final String INPUT_2 = "8=FIX.4.4|9=214|35=D|34=14545|49=TEST|52=20200107-05:57:06.710|56=BEAMERHK1|11=RT-NEW-186423989181027|15=HKD|21=1|22=5|35=D|38=1000|40=2|44=110|48=1|54=1|55=2382|59=0|60=20200107-05:57:06.710|100=SEHK|10=243|";


    @BeforeEach
    void setUp() {
        parser = new StdFixMessageParser();
    }

    @Test
    void testGetSplitSymbol() throws FixMessageParser.ParseException {
        Assertions.assertEquals("\002", FIXMessageHandling.getSplitSymbol(LOGON));
        Assertions.assertEquals("^A", FIXMessageHandling.getSplitSymbol(INPUT_1));
        Assertions.assertEquals("|", FIXMessageHandling.getSplitSymbol(INPUT_2));
    }

    @Test
    void testFixNewOrderSingletoJson() throws IOException, FixMessageParser.ParseException {

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
        Map<Integer, FixTagTranslator> map = XmlParserUtils.loadBuiltinTranslators("src\\FIX.xml");
        JsonFixMessageWriter writer = new JsonFixMessageWriter(jsonBuilder);

        parser.parse(NEW_ORDER_SINGLE, writer, map);
        JsonObject jsonObject = jsonBuilder.build();

        final Map<String, Boolean> jsonMap = ImmutableMap.of(
                JsonGenerator.PRETTY_PRINTING, Boolean.TRUE
        );
        try (final StringWriter stringWriter = new StringWriter();
             final JsonWriter jsonWriter = Json.createWriterFactory(jsonMap).createWriter(stringWriter)) {
            if(jsonWriter!=null){
                jsonWriter.write(jsonObject);
            }
            String jsonString = stringWriter.toString();
            Truth.assertThat(jsonString).isEqualTo(expected);
        }

    }

    @Test
    void testFixNewOrderSingletoString() throws Exception {

        final String expected = "BeginString => FIX.4.4\r\n" +
                "BodyLength => 100\r\n" +
                "MsgType => ORDER_SINGLE\r\n" +
                "ClOrdID => 12345\r\n" +
                "OrdType => MARKET\r\n" +
                "Side => BUY\r\n" +
                "OrderQty => 5000\r\n" +
                "TransactTime => 2003061501:14:49\r\n" +
                "CheckSum => 127\r\n";

        Map<Integer, FixTagTranslator> map = XmlParserUtils.loadBuiltinTranslators("src\\FIX.xml");

        final StringWriter writer = new StringWriter();
        SimpleFixMessageWriter writerToString = new SimpleFixMessageWriter(writer);
        parser.parse(NEW_ORDER_SINGLE, writerToString, map);

        Truth.assertThat(writer.toString()).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {NEW_ORDER_SINGLE, NEW_ORDER_SINGLE_1, LOGON, INPUT_1})
    void testSplitMessage(String message) throws FixMessageParser.ParseException {
        System.out.println(FIXMessageHandling.splitMessage(message));
    }

    @Test
    void test() throws FixMessageParser.ParseException {
        String split1 = FIXMessageHandling.getSplitSymbol(INPUT_1);
        System.out.println(split1);
        StringBuilder split2 = new StringBuilder();
        split2.append("\\").append(split1);
        String[] arrsplit = INPUT_1.split(split2.toString());
        for(String a : arrsplit){
            System.out.println(a);
        }

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
            Map<Integer, FixTagTranslator> map = XmlParserUtils.loadBuiltinTranslators("src\\FIX.xml");
            final StringWriter writer = new StringWriter();
            SimpleFixMessageWriter writerToString = new SimpleFixMessageWriter(writer);

            Assertions.assertThrows(
                    FixMessageParser.ParseException.class,
                    () -> parser.parse(s, writerToString, map)
            );
        }

    }


}
