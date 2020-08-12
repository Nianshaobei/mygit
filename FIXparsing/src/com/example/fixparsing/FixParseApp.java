package com.example.fixparsing;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import org.apache.log4j.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;

@Command(name = "fixApp", description = "Parse FIX messages to file.",
        mixinStandardHelpOptions = true, version = "4.1.3")
public class FixParseApp implements Callable<Integer> {

    @Option(names = {"-f", "--file"}, description = "Configuration file path")
    String configFilePath;
    @Option(names = {"-j", "--json"}, description = "Json output file name")
    String jsonFile;
    @Option(names = {"-s", "--simple"}, description = "Simple output file name")
    String simpleFile;

    private static Logger logger = Logger.getLogger(FixParseApp.class);

    @Override
    public Integer call() throws Exception {

        if (configFilePath != null) {
            FixMessageParser parser = new StdFixMessageParser();
            final Map<Integer, FixTagTranslator> loadedTranslators = XmlParserUtils.loadBuiltinTranslators(configFilePath);

            logger.info("Type the FIX messages by line. Enter 'Q' to exit.");
            Scanner sc = new Scanner(System.in);
            List<String> inputList = new ArrayList<>();
            String s;
            while (!(s = sc.nextLine()).equals("Q")) {
                inputList.add(s);
            }
            sc.close();

            if (null == simpleFile && null == jsonFile) {
                logger.info("The results in simple format are as follows:");
                for (String input : inputList) {
                    try (final StringWriter writer = new StringWriter()) {
                        SimpleFixMessageWriter writeToString = new SimpleFixMessageWriter(writer);
                        parser.parse(input, writeToString, loadedTranslators);
                        logger.info(writer);
                    }
                }
            } else {

                if (null != simpleFile) {
                    // TODO write to file
                    final File f = new File(simpleFile);
                    try (final BufferedWriter writer = Files.newWriter(f, StandardCharsets.UTF_8)) {
                        for (String input : inputList) {
                            SimpleFixMessageWriter writeToString = new SimpleFixMessageWriter(writer);
                            parser.parse(input, writeToString, loadedTranslators);
                            writer.write(System.lineSeparator());
                        }
                    }
                    logger.info("File " + simpleFile + " is generated.");
                }

                if (null != jsonFile) {
                    // TODO write to file
                    final File f = new File(jsonFile);
                    try (final BufferedWriter writer = Files.newWriter(f, StandardCharsets.UTF_8)) {
                        for (String input : inputList) {
                            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
                            JsonFixMessageWriter writerToFile = new JsonFixMessageWriter(jsonBuilder);
                            parser.parse(input, writerToFile, loadedTranslators);
                            final JsonObject jsonObject = jsonBuilder.build();

                            final Map<String, Boolean> map = ImmutableMap.of(
                                    JsonGenerator.PRETTY_PRINTING, Boolean.TRUE
                            );
                            try (final StringWriter stringWriter = new StringWriter();
                                 final JsonWriter jsonWriter = Json.createWriterFactory(map).createWriter(stringWriter)) {
                                if (null != jsonWriter) {
                                    jsonWriter.write(jsonObject);
                                }
                                writer.write(String.valueOf(stringWriter));
                                writer.write(System.lineSeparator());
                            }
                        }
                        logger.info("File " + jsonFile + " is generated.");
                    }
                }

            }

        } else {
            logger.info("Cannot parse without configuration file!>_<" + System.lineSeparator() + "Please enter configuration file path!");
        }

        return 0;
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new FixParseApp()).execute(args));
    }
}
