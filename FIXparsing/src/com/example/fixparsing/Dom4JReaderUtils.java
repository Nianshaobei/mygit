package com.example.fixparsing;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

class Dom4JReaderUtils {

    static String resource = "";

    static Document getDocument() {

        Document document = null;
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(new File(resource));

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return document;
    }
}
