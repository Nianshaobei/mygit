package com.example.fixparsing;

import javax.json.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Scanner;

public class EntryPoint {

    public static void main(String[] args) throws IOException, FixMessageParser.ParseException {

        FixMessageParser parser = new StdFixMessageParser();
        final String input = FixMessage.NEW_ORDER_SINGLE;

        //Get the path entered by the keyboard
        Scanner in = new Scanner(System.in);
        Dom4JReaderUtils.resource = in.nextLine();
        in.close();

        //Print result as string
        FixMessageWriter writeToString = new SimpleFixMessageWriter();
        parser.parse(input, writeToString);
        System.out.println(((SimpleFixMessageWriter) writeToString).getSb());

        //Save result as json object to file
        FixMessageWriter writerToFile = new JsonFixMessageWriter();
        parser.parse(input, writerToFile);
        try {
            File file = new File("json.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(file);
            JsonWriter jsonWriter = Json.createWriter(os);
            jsonWriter.writeObject(((JsonFixMessageWriter) writerToFile).getObject());
            jsonWriter.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
