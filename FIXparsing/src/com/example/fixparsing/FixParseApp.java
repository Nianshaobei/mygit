package com.example.fixparsing;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @Override
    public Integer call() throws Exception {

        FixMessageParser parser = new StdFixMessageParser();

        System.out.println("Type the FIX messages by line. Enter 'Q' to exit.");
        Scanner sc = new Scanner(System.in);
        List<String> inputList = new ArrayList<>();
        String s;
        while (!(s = sc.nextLine()).equals("Q")) {
            inputList.add(s);
        }
        sc.close();

        List<String> outputString = new ArrayList<>();
        List<String> outputJson = new ArrayList<>();
        if (configFilePath != null) {
            for (String input : inputList) {
                //Print result as string
                StringBuilder sb = new StringBuilder();
                SimpleFixMessageWriter writeToString = new SimpleFixMessageWriter(sb);
                parser.parse(input, writeToString, Collections.emptyMap(), configFilePath);
                outputString.add(sb.toString());

                //Save result as json object to file
                JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
                JsonFixMessageWriter writerToFile = new JsonFixMessageWriter(jsonBuilder);
                parser.parse(input, writerToFile, Collections.emptyMap(), configFilePath);
                JsonObject jsonObject = jsonBuilder.build();
                String jsonString = JsonFormatUtils.jsonObject2prettyString(jsonObject);
                outputJson.add(jsonString);
            }

            try {
                if (simpleFile != null) {
                    File file1 = new File(simpleFile);
                    if (!file1.exists()) {
                        file1.createNewFile();
                    }
                    OutputStream os1 = new FileOutputStream(file1);
                    for (String output : outputString) {
                        String newLine = System.getProperty("line.separator");
                        os1.write(output.getBytes());
                        os1.write(newLine.getBytes());
                    }
                    os1.close();
                    System.out.println("File " + simpleFile + " is generated.");
                } else {
                    System.out.println("The results in simple format are as follows:");
                    for (String output : outputString) {
                        System.out.println(output);
                    }
                }

                if (jsonFile != null) {
                    File file2 = new File(jsonFile);
                    if (!file2.exists()) {
                        file2.createNewFile();
                    }
                    OutputStream os2 = new FileOutputStream(file2);
                    for (String output : outputJson) {
                        os2.write(output.getBytes());
                    }
                    os2.close();
                    System.out.println("File " + jsonFile + " is generated.");
                } else {
                    System.out.println("The results in json format are as follows:");
                    for (String output : outputJson) {
                        System.out.println(output);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Please enter configuration file path.");
        }


        return 0;
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new FixParseApp()).execute(args));
    }

}